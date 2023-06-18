package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.model.Question;
import ru.otus.model.Student;
import ru.otus.model.Test;

import java.util.List;

@Service
public class TestService {

    private final IOService ioService;

    private final Converter converter;

    private final QuestionServiceImpl questionService;

    private final int rightAnswer;

    @Autowired
    public TestService(IOService ioService, Converter converter,
                       QuestionServiceImpl questionService, @Value("${rightAnswers}") int rightAnswer) {
        this.ioService = ioService;
        this.converter = converter;
        this.questionService = questionService;
        this.rightAnswer = rightAnswer;
    }

    public void processor() {
        Student student = getStudent();
        Test test = runTest(student);

        if (test.result() > rightAnswer) {
            ioService.println("SUCCESSFUL");
        } else {
            ioService.println("TEST IS FAILED");
        }
        ioService.println("Your result is " + test.result() + " from 5");
    }

    private Student getStudent() {
        String name = ioService.readLineWithPrompt("Enter your name: ");
        String secondName =  ioService.readLineWithPrompt("Enter your second name: ");
        return new Student(name, secondName);
    }

    private Test runTest(Student student) {
        int result = 0;
        String prompt = "Enter the correct answer number (1-5): ";
        List<Question> questions = questionService.getQuestions();

        for (Question question : questions) {
            ioService.printlnWithPrompt(converter.convertQuestionToString(question), prompt);
            int studentAnswer = ioService.readInt();
            if(studentAnswer == question.getNumberRightAnswer()) {
                result++;
            }
        }
        return new Test(student, questions, result);
    }
}
