package ru.otus.service;

import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConverterImpl implements Converter {

    private String convertAnswerToString(int sequenceNumberOfAnswer, Answer answer) {
        return sequenceNumberOfAnswer + ") " + answer.getAnswerText();
    }

    public String convertQuestionToString(Question question) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Question №");
        stringBuilder.append(question.getId());
        stringBuilder.append(": ");
        stringBuilder.append(question.getQuestionText());
        stringBuilder.append("?\n");
        stringBuilder.append("Answers: ");
        stringBuilder.append("\n");
        String answers = IntStream.range(0, question.getAnswers().size())
                .mapToObj(k -> convertAnswerToString(k + 1, question.getAnswers().get(k)))
                .collect(Collectors.joining("\n"));
        stringBuilder.append(answers);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
