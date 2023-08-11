package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс BookDaoJdbc ")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @DisplayName("возвращает ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book expectedBook = new Book(1,"Evgeniy Onegin", author, genre);
        Book actualBook = bookDao.getById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("возвращает ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> books = bookDao.getAll();
        assertEquals(2, books.size());
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        int beforeSize =  bookDao.getAll().size();

        Author author = authorDao.getById(1).get();
        Genre genre = genreDao.getById(1).get();
        Book expectedBook = new Book(1,"Test book", author, genre);

        Book book = bookDao.insert(expectedBook);
        Book actualBook = bookDao.getById(book.getId()).get();
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookDao.getAll().size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() {
        int beforeSize =  bookDao.getAll().size();

        Author author = authorDao.getById(1).get();
        Genre genre = genreDao.getById(1).get();
        Book expectedBook = new Book(1,"Test book", author, genre);

        bookDao.update(expectedBook);
        Book actualBook = bookDao.getById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookDao.getAll().size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет книгу в БД по её id")
    @Test
    void shouldDeleteBook() {
        int beforeSize =  bookDao.getAll().size();

        bookDao.deleteById(1);
        assertThrows(NoSuchElementException.class, () -> bookDao.getById(1).get());

        int afterSize =  bookDao.getAll().size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
