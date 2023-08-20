package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс AuthorRepositoryJdbc ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("возвращает ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(1, "Pushkin");
        Optional<Author> actualAuthor = authorRepository.getById(expectedAuthor.getId());
        assertEquals(Optional.of(expectedAuthor), actualAuthor);
    }

    @DisplayName("возвращает ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> author = authorRepository.getAll();
        assertEquals(4, author.size());
    }

    @DisplayName("добавляет автора в БД если такого автора ещё нет")
    @Test
    void shouldInsertAuthor() throws AuthorExistException {
        int beforeSize =  authorRepository.getAll().size();

        Author expectedAuthor = new Author("Test_author");
        Author author = authorRepository.insert(expectedAuthor);
        Optional<Author> actualAuthor = authorRepository.getById(author.getId());
        assertEquals(Optional.of(expectedAuthor), actualAuthor);

        int afterSize =  authorRepository.getAll().size();
        assertEquals(beforeSize + 1, afterSize);

        Author expectedAuthor1 = new Author("Test_author");
        assertThrows(AuthorExistException.class, () -> authorRepository.insert(expectedAuthor1));

        int afterInsertDuplicateSize =  authorRepository.getAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}