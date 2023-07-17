package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.model.Student;

@ShellComponent
public class ShellService {

    private final TestService testService;

    private final LocalizationService localizationService;

    private Student student;

    @Autowired
    public ShellService(TestService testService, LocalizationService localizationService) {
        this.testService = testService;
        this.localizationService = localizationService;
    }

    @ShellMethod(key = {"t", "test"}, value = "Start test")
    @ShellMethodAvailability(value = "isStartTestAvailable")
    public String start() {
        testService.startTest(student);
        return localizationService.getMessage("output.testIsOver");
    }

    @ShellMethod(key = {"login", "l"}, value = "Enter first name and second name")
    public String login (@ShellOption String name, @ShellOption String secondName) {
        student = new Student(name, secondName);
        return localizationService.getMessage("output.welcome") + name + " " + secondName;
    }

    private Availability isStartTestAvailable() {
        return student == null ?
                Availability.unavailable(localizationService.getMessage("output.LogIn")) :
                Availability.available();
    }
}
