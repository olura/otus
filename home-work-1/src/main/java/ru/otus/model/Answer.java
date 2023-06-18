package ru.otus.model;

import java.util.Objects;

public class Answer {

    private String answerText;

    private boolean isRightAnswer;

    public Answer() {
    }

    public Answer(String answerText) {
        this.answerText = answerText;
        this.isRightAnswer = false;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return isRightAnswer == answer.isRightAnswer && answerText.equals(answer.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerText, isRightAnswer);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                ", isRightAnswer=" + isRightAnswer +
                '}';
    }
}
