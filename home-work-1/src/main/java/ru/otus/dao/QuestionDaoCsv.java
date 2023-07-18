package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoCsv implements QuestionDao {

    private final String fileName;

    public QuestionDaoCsv(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAllQuestions() throws DataLoadingException {
        List<Question> questionList = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);

        try (Reader reader = new InputStreamReader(resource.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            String[] nextRecord = getFirstQuestionLine(csvReader);
            while (nextRecord != null) {
                Question question = parseLineToQuestion(nextRecord).orElseThrow(
                        () -> new DataLoadingException("The string must contain 8 elements"));
                questionList.add(question);
                nextRecord = csvReader.readNext();
            }
        } catch (IOException | CsvValidationException e) {
            throw new DataLoadingException(e.getMessage());
        }
        return questionList;
    }

    private String[] getFirstQuestionLine(CSVReader csvReader)
            throws DataLoadingException, CsvValidationException {
        try {
            String[] nextRecord = csvReader.readNext();
            if (nextRecord == null) {
                throw new DataLoadingException("File " + fileName + " is empty");
            }
            if (Character.isAlphabetic(nextRecord[0].charAt(0))) {
                return csvReader.readNext();
            }
            return nextRecord;
        } catch (IOException e) {
            throw new DataLoadingException("File " + fileName + " not found");
        }
    }

    private Optional<Question> parseLineToQuestion(String[] nextRecord) {

        if (nextRecord.length == 8) {
            return convertLineToQuestion(nextRecord);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Question> convertLineToQuestion(String[] nextRecord) {
        List<Answer> answers = getAnswers(nextRecord);
        return Optional.of(new Question(Integer.parseInt(nextRecord[0]), nextRecord[1], answers));
    }

    private List<Answer> getAnswers(String[] nextRecord) {
        List<Answer> answers = new ArrayList<>();

        for (int i = 2; i < 7; i++) {
            answers.add(new Answer(nextRecord[i]));
        }
        answers.get(Integer.parseInt(nextRecord[7]) - 1).setRightAnswer(true);
        return answers;
    }
}
