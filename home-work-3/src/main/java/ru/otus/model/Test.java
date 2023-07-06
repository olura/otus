package ru.otus.model;

import java.util.List;

public record Test (Student student, List<Question> questionList, int result) {
}
