package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс BookControllerTest ")
@WebMvcTest()
@ContextConfiguration(classes = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("возвращает все книги")
    @Test
    void booksPageShouldReturnCorrectView() throws Exception {
        Author author = new Author("1","Test_author");
        Genre genre = new Genre("1", "Test_genre");
        Book book1 = new Book("1","Test book", author, genre);
        Book book2 = new Book("2","Test book2", author, genre);
        List<Book> bookList = List.of(book1, book2);

        given(bookService.getAll()).willReturn(bookList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", bookList));
    }

    @DisplayName("возвращает книгу со всеми комментариями")
    @Test
    void bookPageShouldReturnCorrectView() throws Exception {
        Author author = new Author("1","Test_author");
        Genre genre = new Genre("1", "Test_genre");
        Book book = new Book("1","Test book", author, genre);
        Comment comment1 = new Comment("Comment 1", book);
        Comment comment2 = new Comment("Comment 2", book);
        List<Comment> commentList = List.of(comment1, comment2);

        given(bookService.getById(book.getId())).willReturn(book);
        given(commentService.getAllCommentToBook(book.getId())).willReturn(commentList);

        mvc.perform(get("/book/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("comments", commentList));
    }

    @DisplayName("возвращает страницу для редактирования книги")
    @Test
    void editPageShouldReturnCorrectView() throws Exception {
        Author author1 = new Author("1","Test_author1");
        Author author2 = new Author("2","Test_author2");
        Genre genre1 = new Genre("1", "Test_genre1");
        Genre genre2 = new Genre("2", "Test_genre2");
        List<Author> authorList = List.of(author1, author2);
        List<Genre> genreList = List.of(genre1, genre2);
        Book book = new Book("1","Test book", author1, genre1);

        given(bookService.getById(book.getId())).willReturn(book);
        given(authorService.getAllAuthors()).willReturn(authorList);
        given(genreService.getAllGenre()).willReturn(genreList);

        mvc.perform(get("/edit?id=" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto(book)))
                .andExpect(model().attribute("authors", authorList))
                .andExpect(model().attribute("genres", genreList));
    }

    @DisplayName("сохраняет книгу")
    @Test
    void saveBookShouldReturnCorrectView() throws Exception {
        Author author = new Author("1","Test_author");
        Genre genre = new Genre("1", "Test_genre");
        Book book = new Book("1","Test book", author, genre);

        given(bookService.save(new BookDto(book))).willReturn(book);

        mvc.perform(put( "/edit"))
                .andExpect(status().is3xxRedirection());
    }


    @DisplayName("возвращает страницу для создания новой книги")
    @Test
    void createBookPageShouldReturnCorrectView() throws Exception {
        Author author1 = new Author("1","Test_author1");
        Author author2 = new Author("2","Test_author2");
        Genre genre1 = new Genre("1", "Test_genre1");
        Genre genre2 = new Genre("2", "Test_genre2");
        List<Author> authorList = List.of(author1, author2);
        List<Genre> genreList = List.of(genre1, genre2);

        given(authorService.getAllAuthors()).willReturn(authorList);
        given(genreService.getAllGenre()).willReturn(genreList);

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", authorList))
                .andExpect(model().attribute("genres", genreList));
    }

    @DisplayName("создаёт новую книгу")
    @Test
    void createBookShouldReturnCorrectView() throws Exception {
        long bookId = 1;
        Author author = new Author("1","Test_author");
        Genre genre = new Genre("1", "Test_genre");
        Book book = new Book("1","Test book", author, genre);

        given(bookService.save(new BookDto(book))).willReturn(book);
        mvc.perform(post("/book"))
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("удаляет книгу")
    @Test
    void deleteBookShouldReturnCorrectView() throws Exception {
        String bookId = "1";

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(bookService).deleteById(valueCapture.capture());

        mvc.perform(delete("/book/" + bookId))
                .andExpect(status().is3xxRedirection());
        assertEquals(bookId, valueCapture.getValue());
    }

}

