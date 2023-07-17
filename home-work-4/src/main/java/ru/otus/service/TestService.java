package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.configuration.AnswerSettingProvider;
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

    private final LocalizationService localizationService;

    private final AnswerSettingProvider appProps;

    @Autowired
    public TestService(IOService ioService, Converter converter, QuestionServiceImpl questionService,
                       LocalizationService localizationService, AnswerSettingProvider appProps) {
        this.ioService = ioService;
        this.converter = converter;
        this.questionService = questionService;
        this.localizationService = localizationService;
        this.appProps = appProps;
    }

    public void startTest(Student student) {
        Test test = testingStudent(student);
        ioService.println(getTestResult(test));
        String statistics = localizationService.getMessage("output.result",
                student.name(), student.secondName(), Integer.toString(test.result()));
        ioService.println(statistics);
    }

    private Test testingStudent(Student student) {
        int result = 0;
        List<Question> questions = getQuestions();

        String prompt = localizationService.getMessage("output.getAnswer");
        for (Question question : questions) {
            ioService.printlnWithPrompt(converter.convertQuestionToString(question, localizationService), prompt);
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
            String error = localizationService.getMessage("output.error");
            ioService.println(error + e.getMessage());
            String testStopped = localizationService.getMessage("output.testStopped");
            ioService.println(testStopped);
        }
        return questions;
    }


    private String getTestResult(Test test) {
        if (test.result() > appProps.getRightAnswer()) {
            return localizationService.getMessage("output.successful");
        } else {
            return localizationService.getMessage("output.failed");
        }
    }
}
