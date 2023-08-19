package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс BookServiceImpl ")
@SpringBootTest(classes = BookServiceImpl.class)
@Import({BookServiceImpl.class})
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDao bookDaoJdbc;

    @MockBean
    private AuthorDao authorDaoJdbc;

    @MockBean
    private GenreDao genreDaoJdbc;

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
    void shouldInsertBook() throws AuthorNotFoundException, GenreNotFoundExeption {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        given(bookDaoJdbc.insert(any())).willReturn(expectedBook);
        given(authorDaoJdbc.getById(anyLong())).willReturn(Optional.of(author));
        given(genreDaoJdbc.getById(anyLong())).willReturn(Optional.of(genre));

        Book actualBook = bookService.insert(
                expectedBook.getTitle(), expectedBook.getAuthor().getId(), expectedBook.getGenre().getId());
        verify(bookDaoJdbc, times(1)).insert(any());
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() throws AuthorNotFoundException, GenreNotFoundExeption {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book expectedBook = new Book(1,"Test book", author, genre);

        ArgumentCaptor<Book> valueCapture = ArgumentCaptor.forClass(Book.class);
        doNothing().when(bookDaoJdbc).update(valueCapture.capture());
        given(authorDaoJdbc.getById(anyLong())).willReturn(Optional.of(author));
        given(genreDaoJdbc.getById(anyLong())).willReturn(Optional.of(genre));

        bookService.update(expectedBook.getId(), expectedBook.getTitle(),
                expectedBook.getAuthor().getId(), expectedBook.getGenre().getId());

        verify(bookDaoJdbc, times(1)).update(any());
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
