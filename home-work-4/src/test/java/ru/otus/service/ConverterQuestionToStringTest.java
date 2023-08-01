package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ConverterImpl ")
@SpringBootTest(classes = ConverterQuestionToString.class)
public class ConverterQuestionToStringTest {
    @MockBean
    private LocalizationService localizationService;
    @Autowired
    private ConverterQuestionToString converter;

    @DisplayName("корректно конвертирует вопрос в строку")
    @Test
    void souldConvertQuestionWithPromptToString() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Spring"));

        Question question = new Question(1, "what is the name of the teacher", answers);
        given(localizationService.getMessage(any())).willReturn("");

        String questionToString = converter.convertQuestionToString(question, localizationService);

        assertThat(questionToString).contains(Integer.toString(question.id()));
        assertThat(questionToString).contains(question.questionText());
        assertThat(questionToString).contains(question.answers().get(0).getAnswerText());

    }
}
