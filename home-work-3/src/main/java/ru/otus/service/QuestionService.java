package ru.otus.service;

import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Optional<List<Question>> getQuestions() throws DataLoadingException;
}
