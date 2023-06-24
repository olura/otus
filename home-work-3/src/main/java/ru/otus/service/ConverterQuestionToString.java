package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ConverterQuestionToString implements Converter {

    private String convertAnswerToString(int sequenceNumberOfAnswer, Answer answer) {
        return sequenceNumberOfAnswer + ") " + answer.getAnswerText();
    }

    @Override
    public String convertQuestionToString(Question question) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Question №");
        stringBuilder.append(question.id());
        stringBuilder.append(": ");
        stringBuilder.append(question.questionText());
        stringBuilder.append("?\n");
        stringBuilder.append("Answers: ");
        stringBuilder.append("\n");
        String answers = IntStream.range(0, question.answers().size())
                .mapToObj(k -> convertAnswerToString(k + 1, question.answers().get(k)))
                .collect(Collectors.joining("\n"));
        stringBuilder.append(answers);
        return stringBuilder.toString();
    }
}
