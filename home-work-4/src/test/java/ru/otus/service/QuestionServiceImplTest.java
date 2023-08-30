package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.QuestionDao;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс QuestionServiceImplTest ")
@SpringBootTest(classes = QuestionServiceImpl.class)
public class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;
    @Autowired
    private QuestionServiceImpl questionService;

    @DisplayName("должен корректно возвращать список вопросов")
    @Test
    void shouldCorrectGetQuestions() throws DataLoadingException {
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Tom"));
        answers.add(new Answer("Bob"));
        answers.add(new Answer("Aleksandr"));
        answers.add(new Answer("Mike"));
        answers.add(new Answer("Ivan"));
        answers.get(3).setItIsRightAnswer();
        questions.add(new Question(1, "what is the name of the teacher", answers));
        given(questionDao.getAllQuestions()).willReturn(questions);

        assertEquals(questionService.getQuestions(), questions);
    }
}
