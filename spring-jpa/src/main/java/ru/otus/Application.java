package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.exception.AuthorExistException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws AuthorExistException {
		SpringApplication.run(Application.class, args);
	}
}
