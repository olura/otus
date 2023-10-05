package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс AuthorRepositoryJdbc ")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("возвращает ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author("1", "Pushkin");
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertEquals(Optional.of(expectedAuthor), actualAuthor);
    }

    @DisplayName("возвращает ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> author = authorRepository.findAll();
        assertEquals(4, author.size());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("сохраняет автора в БД")
    @Test
    void shouldInsertAuthor() {

        int beforeSize =  authorRepository.findAll().size();
        Author expectedAuthor = new Author("Test_author");
        Author author = authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.findById(author.getId()).get();
        assertEquals(expectedAuthor, actualAuthor);
        int afterSize =  authorRepository.findAll().size();
        assertEquals(beforeSize + 1, afterSize);

        expectedAuthor.setName("New author");
        Author author1 = authorRepository.save(expectedAuthor);
        Author actualAuthor1 = authorRepository.findById(author1.getId()).get();
        assertEquals(expectedAuthor, actualAuthor1);
        int afterInsertDuplicateSize = authorRepository.findAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}