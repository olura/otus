package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.exception.AuthorExistException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws AuthorExistException {
//		ApplicationContext context =
		SpringApplication.run(Application.class, args);
//		AuthorRepository authorRepository = context.getBean(AuthorRepositoryJpa.class);
//		Author author = new Author("Pushkin1");
//		Author author1 = new Author("Pushkin");
//		System.out.println(authorRepository.insert(author));
//		System.out.println(authorRepository.insert(author));
//		System.out.println(authorRepository.getAll());

	}
}
