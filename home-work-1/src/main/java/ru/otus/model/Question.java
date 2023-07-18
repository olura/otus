package ru.otus.model;

import java.util.List;
import java.util.Objects;

public class Question {

    private int id;

    private String questionText;

    private List<Answer> answers;

    public Question() {
    }

    public Question(int id, String questionText, List<Answer> answersText) {
        this.id = id;
        this.questionText = questionText;
        this.answers = answersText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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
        return questionText.equals(question.questionText) && answers.equals(question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answers);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", answers=" + answers +
                '}';
    }
}
