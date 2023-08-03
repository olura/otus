package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorDaoORM;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс BookServiceImpl ")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbc.class, AuthorDaoORM.class, GenreDaoJdbc.class})
public class BookServiceImplTest {

    private final BookServiceImpl bookService;

    @Autowired
    public BookServiceImplTest(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @DisplayName("возвращает ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book expectedBook = new Book(1,"Evgeniy Onegin", author, genre);
        Book actualBook = bookService.getById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("возвращает ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> books = bookService.getAll();
        assertEquals(2, books.size());
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        int beforeSize =  bookService.getAll().size();

        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        Book book = bookService.insert(expectedBook);
        Book actualBook = bookService.getById(book.getId()).get();
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookService.getAll().size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() {
        int beforeSize =  bookService.getAll().size();

        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        bookService.update(expectedBook);
        Book actualBook = bookService.getById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookService.getAll().size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет книгу в БД по её id")
    @Test
    void shouldDeleteBook() {
        int beforeSize =  bookService.getAll().size();

        bookService.deleteById(1);
        assertThrows(NoSuchElementException.class, () -> bookService.getById(1).get());

        int afterSize =  bookService.getAll().size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
