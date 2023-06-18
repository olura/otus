package ru.otus.model;

import java.util.List;

public record Test (Student student, List<Question> questionList, int result) {
//
//    @Override
//    public String toString() {
//        return "Test{" +
//                "student=" + student +
//                ", questionList=" + questionList +
//                ", result=" + result +
//                '}';
//    }
}
