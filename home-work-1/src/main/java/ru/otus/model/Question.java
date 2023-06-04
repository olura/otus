package ru.otus.model;

import java.util.List;
import java.util.Objects;

public class Question {
    private int id;

    private String questionText;

    private List<String> answersText;

    private String rightAnswer;

    public Question(){
    }

    public Question(int id, String questionText, List<String> answersText, String rightAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.answersText = answersText;
        this.rightAnswer = rightAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(questionText, question.questionText) && Objects.equals(answersText, question.answersText)
                && Objects.equals(rightAnswer, question.rightAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answersText, rightAnswer);
    }

    @Override
    public String toString() {
        return "Question: " + questionText + '\n' +
                "Answers: \n1. " + answersText.get(0) + '\n' +
                "2. " + answersText.get(1) + '\n' +
                "3. " + answersText.get(2) + '\n' +
                "4. " + answersText.get(3) + '\n' +
                "5. " + answersText.get(4);
    }
}
