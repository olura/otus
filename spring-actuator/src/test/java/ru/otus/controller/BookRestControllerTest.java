package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс BookController ")
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;


    @DisplayName("GET /api/book возвращает HTTP-ответ со статусом 200 OK и списком книг")
    @Test
    void getAllBookShouldReturnAllBooks() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book1 = new Book(1,"Test book", author, genre);
        Book book2 = new Book(2,"Test book2", author, genre);
        List<Book> bookList = List.of(book1, book2);

        given(bookService.getAll()).willReturn(bookList);

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @DisplayName("GET /api/book/1 возвращает HTTP-ответ со статусом 200 OK и книгу")
    @Test
    void getBookShouldReturnValidBook() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);

        given(bookService.getById(book.getId())).willReturn(book);

        mvc.perform(get("/api/book/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test book"))
                .andExpect(jsonPath("$.author").value("Test_author"))
                .andExpect(jsonPath("$.genre").value("Test_genre"));
    }

    @DisplayName("POST /api/book возвращает HTTP-ответ со статусом 200 OK и сохранённую книгу")
    @Test
    void createBookShouldReturnValidBook() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new BookDto(book));

        given(bookService.saveNewBook(new BookDto(book))).willReturn(book);
        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("Test book"))
                        .andExpect(jsonPath("$.author").value("Test_author"))
                        .andExpect(jsonPath("$.genre").value("Test_genre"));
    }

    @DisplayName("PUT /api/book/1 возвращает HTTP-ответ со статусом 200 OK и сохранённую книгу")
    @Test
    void editBookShouldReturnValidBook() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new BookDto(book));

        given(bookService.saveEditBook(new BookDto(book), book.getId())).willReturn(book);

        mvc.perform(put("/api/book/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value("Test book"))
                .andExpect(jsonPath("$.author").value("Test_author"))
                .andExpect(jsonPath("$.genre").value("Test_genre"));
    }

    @DisplayName("DELETE /api/book/1 возвращает HTTP-ответ со статусом 200 OK и статус success")
    @Test
    void deleteBookShouldStateSuccess() throws Exception {
        long bookId = 1;

        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(bookService).deleteById(valueCapture.capture());

        mvc.perform(delete("/api/book/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("success"));
        assertEquals(bookId, valueCapture.getValue());
    }
}

