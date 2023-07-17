package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Question;

@Service
public interface Converter {
    String convertQuestionToString(Question question, LocalizationService localizationService);
}
