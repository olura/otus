package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.model.Question;

public class QuestionService {

    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void runTest() {
        for (Question question : questionDao.getAllQuestions()) {
            System.out.println("Question №" + question.getId() + ": " + question.getQuestionText() + "?");
            System.out.println("Answers: ");
            for (int i = 0; i < question.getAnswers().size(); i++) {
                System.out.println(i + 1 + ") " + question.getAnswers().get(i).getAnswerText() + " -> "
                        + question.getAnswers().get(i).isRightAnswer());
            }
            System.out.println();
        }
    }
}

