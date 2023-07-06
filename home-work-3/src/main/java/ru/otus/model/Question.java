package ru.otus.model;

import java.util.List;

public record Question(int id, String questionText, List<Answer> answers) {
    public int getNumberRightAnswer() {
        int numberRightAnswer = 0;
        for (Answer answer: answers) {
            numberRightAnswer++;
            if (answer.isRightAnswer()) {
                break;
            }
        }
        return numberRightAnswer;
    }
}
