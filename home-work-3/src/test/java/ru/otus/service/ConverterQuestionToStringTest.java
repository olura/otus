package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.configuration.LocaleProvider;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ConverterImpl ")
@ExtendWith(MockitoExtension.class)
public class ConverterQuestionToStringTest {

    @Mock
    private LocaleProvider localeProvider;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private LocalizationService localizationService;

    @DisplayName("корректно конвертирует вопрос в строку")
    @Test
    void souldConvertQuestionWithPromptToString() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Spring"));

        Question question = new Question(1, "what is the name of the teacher", answers);
        given(messageSource.getMessage(any(), any(), any())).willReturn("");

        ConverterQuestionToString converter = new ConverterQuestionToString(localizationService);
        String questionToString = converter.convertQuestionToString(question);

        assertThat(questionToString).contains(Integer.toString(question.id()));
        assertThat(questionToString).contains(question.questionText());
        assertThat(questionToString).contains(question.answers().get(0).getAnswerText());

    }
}
