package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс QuestionDaoCsv ")
public class QuestionDaoCsvTest {

    private final QuestionDao questionDao = new QuestionDaoCsv("questions.csv");

    @Test
    @DisplayName("загружает все вопросы имеющиеся в файле")
    void shouldLoadQuestions() throws DataLoadingException {
        List<Question> questions = questionDao.getAllQuestions();

        assertEquals(2, questions.size());
    }

    @Test
    @DisplayName("корректно загружает текст вопроса")
    void shouldLoadQuestionText() throws DataLoadingException {
        List<Question> questions = questionDao.getAllQuestions();

        assertThat(questions.toString()).contains("what is the name of the teacher");
        assertThat(questions.toString()).contains("what is the name of the subject");
    }

    @Test
    @DisplayName("загружает все ответы для каждого из вопросов")
    void shouldLoadAnswers() throws DataLoadingException {
        List<Question> questions = questionDao.getAllQuestions();
        for (Question question : questions) {
            assertEquals(5, question.answers().size());
        }
    }

    @Test
    @DisplayName("корректно загружает текст ответов")
    void shouldLoadAnswerText() throws DataLoadingException {
        List<Question> questions = questionDao.getAllQuestions();

        assertThat(questions.toString()).contains("Tom");
        assertThat(questions.toString()).contains("Bob");
        assertThat(questions.toString()).contains("Aleksandr");
        assertThat(questions.toString()).contains("Mike");
        assertThat(questions.toString()).contains("Ivan");
    }

    @Test
    @DisplayName("корректно загружает правильный ответ")
    void shouldLoadRightAnswer() throws DataLoadingException {
        List<Question> questions = questionDao.getAllQuestions();

        assertEquals(questions.get(0).getNumberRightAnswer(), 3);
        assertEquals(questions.get(1).getNumberRightAnswer(), 2);
    }
}
