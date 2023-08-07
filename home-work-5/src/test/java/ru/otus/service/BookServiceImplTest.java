package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorDaoJdbc;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@DisplayName("Класс BookServiceImpl ")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("возвращает ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book expectedBook = new Book(1,"Evgeniy Onegin", author, genre);

        given(bookDaoJdbc.getById(anyLong())).willReturn((Optional.of(expectedBook)));

        Book actualBook = bookService.getById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("возвращает ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {

        Author author1 = new Author(1,"Pushkin");
        Genre genre1 = new Genre(1, "Romance");
        Author author2 = new Author(2,"Test_author");
        Genre genre2 = new Genre(2, "Test_genre");

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1,"Evgeniy Onegin", author1, genre1));
        expectedBooks.add(new Book(2,"Test book", author2, genre2));

        given(bookDaoJdbc.getAll()).willReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAll();
        assertEquals(expectedBooks, actualBooks);
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        given(bookDaoJdbc.insert(any())).willReturn(expectedBook);

        Book actualBook = bookService.insert(expectedBook);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        ArgumentCaptor<Book> valueCapture = ArgumentCaptor.forClass(Book.class);
        doNothing().when(bookDaoJdbc).update(valueCapture.capture());

        bookService.update(expectedBook);
        assertEquals(expectedBook, valueCapture.getValue());
    }

    @DisplayName("удаляет книгу в БД по её id")
    @Test
    void shouldDeleteBook() {

        long id = 1;

        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(bookDaoJdbc).deleteById(valueCapture.capture());

        bookService.deleteById(id);
        assertEquals(id, valueCapture.getValue());
    }
}
