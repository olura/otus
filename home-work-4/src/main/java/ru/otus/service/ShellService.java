package ru.otus.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellService {

    private final TestService testService;

    public ShellService(TestService testService) {
        this.testService = testService;
    }

    @ShellMethod(key = {"t", "test"}, value = "Start test")
    public void start() {
        testService.startTesting();
    }

}
