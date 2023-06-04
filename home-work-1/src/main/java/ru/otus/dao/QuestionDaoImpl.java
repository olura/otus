package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
import ru.otus.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao{

    private final String fileName;

    public QuestionDaoImpl (String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);
        try (Reader reader = new InputStreamReader(resource.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            Question question;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (Character.isAlphabetic(nextRecord[0].charAt(0))) {
                    continue;
                }
                question = new Question(
                        Integer.parseInt(nextRecord[0]), nextRecord[1],
                        Arrays.stream(new String[] {nextRecord[2], nextRecord[3], nextRecord[4],
                                nextRecord[5], nextRecord[6]}).toList(), nextRecord[7]);
            questionList.add(question);
            }
        } catch (IOException e) {
            System.out.println("Failed to open csv file " + fileName);
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return questionList;
    }
}
