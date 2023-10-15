package ru.otus.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.service.BookService;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Autowired
    public CustomHealthIndicator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {
        long numberOfBook = bookService.getAll().size();
        if (numberOfBook == 0) {
            return Health.down().withDetail("message", "The book library is empty").build();
        } else if (numberOfBook > 100) {
            return Health.unknown().withDetail("message", "The book library is full").build();
        } else {
            return Health.up().withDetail("message", "It's good").build();
        }
    }
}
