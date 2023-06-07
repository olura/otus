package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
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
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);

        try (Reader reader = new InputStreamReader(resource.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Optional<Question> questionOptional = parseLineToQuestion(nextRecord);
                if (questionOptional.isEmpty()) {
                    continue;
                }
                questionList.add(questionOptional.get());
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    private Optional<Question> parseLineToQuestion(String[] nextRecord) throws CsvValidationException {
        Question question;
        List<Answer> answers = new ArrayList<>();

        if (Character.isAlphabetic(nextRecord[0].charAt(0))) {
            return Optional.empty();
        }
        if (nextRecord.length != 8) {
            throw new CsvValidationException("The string must contain 8 elements");
        }
        for (int i = 2; i < 7; i++) {
            answers.add(new Answer(nextRecord[i]));
        }
        answers.get(Integer.parseInt(nextRecord[7])).setRightAnswer(true);
        question = new Question(Integer.parseInt(nextRecord[0]), nextRecord[1], answers);
        return Optional.of(question);
    }
}
