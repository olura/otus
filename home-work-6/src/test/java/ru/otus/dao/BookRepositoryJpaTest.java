package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс BookRepositoryJpaTest ")
@DataJpaTest
@Import({BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращает ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book expectedBook = new Book(1,"Evgeniy Onegin", author, genre);
        Optional<Book> actualBook = bookRepository.getById(expectedBook.getId());
        assertEquals(Optional.of(expectedBook), actualBook);
    }

    @DisplayName("возвращает ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> books = bookRepository.getAll();
        System.out.println(books);
        assertEquals(2, books.size());
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        int beforeSize =  bookRepository.getAll().size();

        Author author = authorRepository.getById(1).get();
        Genre genre = genreRepository.getById(1).get();
        Book expectedBook = new Book("Test book", author, genre);

        Book book = bookRepository.insert(expectedBook);
        Optional<Book> actualBook = bookRepository.getById(book.getId());
        assertEquals(Optional.of(expectedBook), actualBook);

        int afterSize =  bookRepository.getAll().size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() {
        int beforeSize =  bookRepository.getAll().size();
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book expectedBook = new Book(1,"Test book", author, genre);

        bookRepository.update(expectedBook);
        Optional<Book> actualBook = bookRepository.getById(expectedBook.getId());
        assertEquals(Optional.of(expectedBook), actualBook);

        int afterSize =  bookRepository.getAll().size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет книгу в БД по её id")
    @Test
    void shouldDeleteBook() throws NoFoundBookException {
        int beforeSize =  bookRepository.getAll().size();

        bookRepository.deleteById(1);
        assertThrows(NoSuchElementException.class, () -> bookRepository.getById(1).orElseThrow());

        int afterSize =  bookRepository.getAll().size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
