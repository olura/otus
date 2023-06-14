package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.exception.DataInputException;
import ru.otus.model.Question;

import java.util.List;

public class QuestionService {

    private final QuestionDao questionDao;

    private final IOService output;

    private final Converter converter;

    public QuestionService(QuestionDao questionDao, IOService output, Converter converter) {
        this.questionDao = questionDao;
        this.output = output;
        this.converter = converter;
    }

    public void runTest() {
        try {
            List<Question> questions = questionDao.getAllQuestions();
            questions.stream().map(converter::convertQuestionToString).forEach(output::println);
        } catch (DataInputException e) {
            System.err.println(e.getMessage());
        }
    }
}

