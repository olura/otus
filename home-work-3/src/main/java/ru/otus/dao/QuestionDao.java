package ru.otus.dao;

import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionDao {
    Optional <List<Question>> getAllQuestions() throws DataLoadingException;
}
