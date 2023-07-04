package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configuration.AppProps;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;
import ru.otus.model.Student;
import ru.otus.model.Test;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final IOService ioService;

    private final Converter converter;

    private final QuestionServiceImpl questionService;

    private final MessageSource messageSource;

    private final AppProps appProps;

    @Autowired
    public TestService(IOService ioService, Converter converter,
                       QuestionServiceImpl questionService, MessageSource messageSource, AppProps appProps) {
        this.ioService = ioService;
        this.converter = converter;
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    public void startTest() {
        Student student = getStudent();
        Test test = testingStudent(student);
        String result = getTestResult(test);
        ioService.println(result);
        String statistics = messageSource.getMessage("output.result",
                new String[]{student.name(), student.secondName(), Integer.toString(test.result())},
                appProps.locale());
        ioService.println(statistics);
    }

    private Student getStudent() {
        String messageforUser;
        
        messageforUser = messageSource.getMessage("get.username", new String[]{}, appProps.locale());
        String name = ioService.readLineWithPrompt(messageforUser);
        
        messageforUser = messageSource.getMessage("get.secondName", new String[]{}, appProps.locale());
        String secondName =  ioService.readLineWithPrompt(messageforUser);
        
        return new Student(name, secondName);
    }

    private Test testingStudent(Student student) {
        int result = 0;
        List<Question> questions = getQuestions();

        String prompt = messageSource.getMessage("output.getAnswer", new String[]{}, appProps.locale());
        for (Question question : questions) {
            ioService.printlnWithPrompt(converter.convertQuestionToString(question), prompt);
            int studentAnswer = ioService.readInt();
            if (studentAnswer == question.getNumberRightAnswer()) {
                result++;
            }
        }
        return new Test(student, questions, result);
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            questions = questionService.getQuestions();
        } catch (DataLoadingException e) {
            String error = messageSource.getMessage("output.error", new String[]{}, appProps.locale());
            ioService.println(error + e.getMessage());
            String testStopped = messageSource.getMessage("output.testStopped", new String[]{}, appProps.locale());
            ioService.println(testStopped);
        }
        return questions;
    }


    private String getTestResult(Test test) {
        if (test.result() > appProps.rightAnswers()) {
            return messageSource.getMessage("output.successful", new String[]{}, appProps.locale());
        } else {
            return messageSource.getMessage("output.failed", new String[]{}, appProps.locale());
        }
    }
}
