package ru.otus.service;

import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.io.InputStream;
import java.io.PrintStream;

public class IOService {

    private final PrintStream output;

    private final InputStream input;

    public IOService(PrintStream output, InputStream input) {
        this.output = output;
        this.input = input;
    }

    void println(String str) {
        output.println(str);
    }

    void println() {
        output.println();
    }

    void printQuestion(Question question) {
        output.println("Question №" + question.getId() + ": " + question.getQuestionText() + "?");
    }

    void printAnswer(int sequenceNumberOfAnswer, Answer answer) {
        output.println(sequenceNumberOfAnswer + 1 + ") " + answer.getAnswerText() + " -> "
                + answer.isRightAnswer());
    }
}
