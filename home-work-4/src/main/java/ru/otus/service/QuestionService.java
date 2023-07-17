package ru.otus.service;

import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions() throws DataLoadingException;
}
