package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configuration.AppProps;
import ru.otus.model.Question;
import ru.otus.model.Student;
import ru.otus.model.Test;

import java.util.List;

@Service
public class TestService implements CommandLineRunner {

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

    private Student getStudent() {
        String messageLocalization = messageSource.getMessage("get.username", new String[]{}, appProps.locale());
        String name = ioService.readLineWithPrompt(messageLocalization);
        messageLocalization = messageSource.getMessage("get.second_name", new String[]{}, appProps.locale());
        String secondName =  ioService.readLineWithPrompt(messageLocalization);
        return new Student(name, secondName);
    }

    private Test runTest(Student student) {
        int result = 0;
        String prompt = messageSource.getMessage("enter.answer", new String[]{}, appProps.locale());
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

    @Override
    public void run(String... args) {
        Student student = getStudent();
        Test test = runTest(student);
        var succecful = messageSource.getMessage("output.succecful", new String[]{}, appProps.locale());
        var failed = messageSource.getMessage("output.failed", new String[]{}, appProps.locale());
        var result = messageSource.getMessage("output.result",
                new String[]{student.name(), student.secondName(), Integer.toString(test.result())},
                appProps.locale());

        if (test.result() > appProps.rightAnswers()) {
            ioService.println(succecful);
        } else {
            ioService.println(failed);
        }
        ioService.println(result);
    }
}
