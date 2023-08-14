package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import ru.otus.dao.AuthorRepository;
//import ru.otus.dao.AuthorRepositoryJpa;
//import ru.otus.domain.Author;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		ApplicationContext context =
		SpringApplication.run(Application.class, args);
//		AuthorRepository authorRepository = context.getBean(AuthorRepositoryJpa.class);
//		Author author = new Author("Pushkin");
//		Author author1 = new Author("Pushkin1");
//		System.out.println(authorRepository.insert(author));
//		System.out.println(authorRepository.insert(author1));
//		System.out.println(authorRepository.getById(2));

	}
}
