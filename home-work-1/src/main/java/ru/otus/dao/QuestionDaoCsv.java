package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
import ru.otus.exception.DataInputException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoCsv implements QuestionDao{

    private final String fileName;

    public QuestionDaoCsv(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAllQuestions() throws DataInputException {
        List<Question> questionList = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);

        try (Reader reader = new InputStreamReader(resource.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Optional<Question> questionOptional = parseLineToQuestion(nextRecord);
                questionOptional.ifPresent(questionList::add);
            }
        } catch (IOException e) {
            throw new DataInputException("File " + fileName + " not found");
        } catch (CsvValidationException e) {
            throw new DataInputException(e.getMessage());
        }
        return questionList;
    }

    private Optional<Question> parseLineToQuestion(String[] nextRecord) throws DataInputException {
        boolean isRightLine = validateString(nextRecord);

        if (isRightLine) {
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

    private boolean validateString(String[] nextRecord) throws DataInputException {
        if (Character.isAlphabetic(nextRecord[0].charAt(0))) {
            return false;
        }
        if (nextRecord.length != 8) {
            throw new DataInputException("The string must contain 8 elements");
        }
        return true;
    }
}
