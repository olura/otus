package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.configuration.ApplicationConfiguration;
import ru.otus.service.TestService;

public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        TestService testService = context.getBean(TestService.class);
        testService.processor();
    }
}
