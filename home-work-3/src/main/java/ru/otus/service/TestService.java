package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configuration.AppProps;
import ru.otus.exception.DataLoadingException;
import ru.otus.model.Question;
import ru.otus.model.Student;
import ru.otus.model.Test;

import java.util.List;
import java.util.Optional;

import static java.lang.System.exit;

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
        String messageforUser;
        
        messageforUser = messageSource.getMessage("get.username", new String[]{}, appProps.locale());
        String name = ioService.readLineWithPrompt(messageforUser);
        
        messageforUser = messageSource.getMessage("get.secondName", new String[]{}, appProps.locale());
        String secondName =  ioService.readLineWithPrompt(messageforUser);
        
        return new Student(name, secondName);
    }

    private Test runTest(Student student) {
        int result = 0;
        List<Question> questions = getQuestionsFromOptional();

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

    private List<Question> getQuestionsFromOptional() {
        Optional<List<Question>> questions = Optional.empty();
        String messageforUser;

        try {
            questions = questionService.getQuestions();
        } catch (DataLoadingException e) {
            messageforUser = messageSource.getMessage("output.error", new String[]{}, appProps.locale());
            ioService.println(messageforUser + e.getMessage());
            if (e.getCause() != null) {
                ioService.println(e.getCause().getMessage());
            }
        }
        if (questions.isPresent()) {
            return questions.get();
        } else {
            messageforUser = messageSource.getMessage("output.testStopped", new String[]{}, appProps.locale());
            ioService.println(messageforUser);
            exit(1);
            return null;
        }
    }

    @Override
    public void run(String... args) {
        Student student = getStudent();
        Test test = runTest(student);
        String succecful = messageSource.getMessage("output.successful", new String[]{}, appProps.locale());
        String failed = messageSource.getMessage("output.failed", new String[]{}, appProps.locale());
        String result = messageSource.getMessage("output.result",
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
