package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.exception.DataInputException;
import ru.otus.model.Question;

public class QuestionService {

    private final QuestionDao questionDao;

    private final IOService output;

    public QuestionService(QuestionDao questionDao, IOService output) {
        this.questionDao = questionDao;
        this.output = output;
    }

    public void runTest() {
        try {
            for (Question question : questionDao.getAllQuestions()) {
                output.printQuestion(question);
                output.println("Answers: ");
                for (int i = 0; i < question.getAnswers().size(); i++) {
                    output.printAnswer(i + 1, question.getAnswers().get(i));
                }
                output.println();
            }
        } catch (DataInputException e) {
            System.err.println(e.getMessage());
        }
    }
}

