package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс конвертер ")
public class ConverterImplTest {

    private final Converter converter = new ConverterImpl();

    @DisplayName("корректно конвертирует вопрос в строку")
    @Test
    void souldConvertQuestionWithPromptToString() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Spring"));

        Question question = new Question(1, "what is the name of the teacher", answers);
        String questionToString = converter.convertQuestionToString(question);

        assertThat(questionToString).contains(Integer.toString(question.id()));
        assertThat(questionToString).contains(question.questionText());
        assertThat(questionToString).contains(question.answers().get(0).getAnswerText());

    }
}
