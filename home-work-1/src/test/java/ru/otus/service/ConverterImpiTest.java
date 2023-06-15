package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс конвертер ")
public class ConverterImpiTest {

    private final Converter converter = new ConverterImpl();

    @DisplayName("корректно конвертирует вопрос в строку")
    @Test
    void souldConvertQuestionWithPromptToString() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Spring"));

        Question question = new Question(1, "what is the name of the teacher", answers);
        String questionToString = converter.convertQuestionToString(question);

        assertThat(questionToString).contains(Integer.toString(question.getId()));
        assertThat(questionToString).contains(question.getQuestionText());
        assertThat(questionToString).contains(question.getAnswers().get(0).getAnswerText());

    }
}
