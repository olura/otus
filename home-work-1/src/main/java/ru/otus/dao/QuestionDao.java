package ru.otus.dao;

import ru.otus.exception.DataInputException;
import ru.otus.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getAllQuestions() throws DataInputException;
}
