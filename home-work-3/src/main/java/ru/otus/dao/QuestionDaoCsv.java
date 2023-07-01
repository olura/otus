package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.configuration.FileSourceProvider;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionDaoCsv implements QuestionDao{

    private final FileSourceProvider fileSourceProvider;

    @Autowired
    public QuestionDaoCsv(FileSourceProvider fileSourceProvider) {
        this.fileSourceProvider = fileSourceProvider;
    }

    @Override
    public List<Question> getAllQuestions() throws DataLoadingException {
        List<Question> questionList = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileSourceProvider.getFileName());

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
            throw new DataLoadingException("Error during questions loading", e);
        }
        return questionList;
    }

    private String[] getFirstQuestionLine(CSVReader csvReader)
            throws DataLoadingException, CsvValidationException {
        try {
            String[] nextRecord = csvReader.readNext();
            if (nextRecord == null) {
                throw new DataLoadingException("File is empty");
            }
            if (Character.isAlphabetic(nextRecord[0].charAt(0))) {
                return csvReader.readNext();
            }
            return nextRecord;
        } catch (IOException e) {
            throw new DataLoadingException("File not found", e);
        }
    }

    private Optional<Question> parseLineToQuestion(String[] nextRecord) throws DataLoadingException {

        if (nextRecord.length == 8) {
            return convertLineToQuestion(nextRecord);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Question> convertLineToQuestion(String[] nextRecord) throws DataLoadingException {
        List<Answer> answers = getAnswers(nextRecord);
        return Optional.of(new Question(Integer.parseInt(nextRecord[0]), nextRecord[1], answers));
    }

    private List<Answer> getAnswers(String[] nextRecord) throws DataLoadingException {
        List<Answer> answers = new ArrayList<>();
        int numberRightAnswer = 0;
        String answer = nextRecord[7];

        if (answer.length() > 0 && Character.isDigit(answer.charAt(0))) {
            numberRightAnswer = Integer.parseInt(answer) - 1;
        }

        for (int i = 2; i < 7; i++) {
            answers.add(new Answer(nextRecord[i]));
        }
        if (numberRightAnswer > 5 || numberRightAnswer < 1) {
            throw new DataLoadingException("The sequence number of correct answer is incorrect");
        }
        answers.get(numberRightAnswer).setItIsRightAnswer();
        return answers;
    }
}
