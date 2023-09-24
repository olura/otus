package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс AuthorRepositoryJdbc ")
@DataJpaTest
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(1, "Pushkin");
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertEquals(Optional.of(expectedAuthor), actualAuthor);
    }

    @DisplayName("возвращает ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> author = authorRepository.findAll();
        assertEquals(4, author.size());
    }

    @DisplayName("сохраняет автора в БД")
    @Test
    void shouldInsertAuthor() {
        int beforeSize =  authorRepository.findAll().size();
        Author expectedAuthor = new Author("Test_author");
        Author author = authorRepository.save(expectedAuthor);
        Author actualAuthor = entityManager.find(Author.class, author.getId());
        assertEquals(expectedAuthor, actualAuthor);
        int afterSize =  authorRepository.findAll().size();
        assertEquals(beforeSize + 1, afterSize);

        expectedAuthor.setName("New author");
        Author author1 = authorRepository.save(expectedAuthor);
        Author actualAuthor1 = entityManager.find(Author.class, author1.getId());
        assertEquals(expectedAuthor, actualAuthor1);
        int afterInsertDuplicateSize = authorRepository.findAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}