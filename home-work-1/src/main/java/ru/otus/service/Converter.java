package ru.otus.service;

import ru.otus.model.Question;

public interface Converter {
    String convertQuestionToString(Question question);
}
