package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.model.Question;

import java.util.Scanner;

public class QuestionService {

    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void runTest() {
        for (Question question : questionDao.getAllQuestions()) {
            printQuestion(question);
        }
    }

    private void printQuestion(Question question) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.println(question);
        answer = scanner.nextLine();
        if (question.getRightAnswer().equals(answer)) {
            System.out.println(setColor("OK", Colors.GREEN));
        } else {
            System.out.println(setColor("ERROR", Colors.RED));
        }
    }

    private String setColor(String text, Colors color) {
        final String ansiRed = "\u001B[31m";
        final String ansiGreen = "\u001B[32m";
        final String ansiReset = "\u001B[0m";

        StringBuilder stringBuilder = new StringBuilder();

        switch (color) {
            case RED -> stringBuilder.append(ansiRed);
            case GREEN -> stringBuilder.append(ansiGreen);
        }
        stringBuilder.append(">>> ");
        stringBuilder.append(text);
        stringBuilder.append(ansiReset);
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    private enum Colors {
        RED,
        GREEN
    }

}

