package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.config.SecurityConfiguration;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс BookControllerTest пользователю с ролью USER ")
@WebMvcTest({BookController.class, SecurityConfiguration.class})
@WithMockUser( roles = {"USER"})
public class BookControllerSecurityUserTest {

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
    private final long bookId = 1;


    @DisplayName("разрешает доступ к странице логина")
    @Test
    void givenAccessToLoginPage() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
    @DisplayName("разрешает доступ к корневой странице")
    @Test
    void forbiddenBooksPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @DisplayName("разрешает доступ к странице книги")
    @Test
    void forbiddenBookPage() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);
        Comment comment1 = new Comment("Comment 1", book);
        Comment comment2 = new Comment("Comment 2", book);
        List<Comment> commentList = List.of(comment1, comment2);

        given(bookService.getById(book.getId())).willReturn(book);
        given(commentService.getAllCommentToBook(book.getId())).willReturn(commentList);

        mvc.perform(get("/book/" + bookId))
                .andExpect(status().isOk());
    }

    @DisplayName("запрещает доступ к странице для редактирования книги")
    @Test
    void forbiddenBookEditPage() throws Exception {
        mvc.perform(get("/edit?id=" + bookId))
                .andExpect(status().isForbidden());
    }

    @DisplayName("запрещает сохранять книгу")
    @Test
    void forbiddenToSaveBook() throws Exception {
        mvc.perform(put( "/edit").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("запрещает возвращать страницу для создания новой книги")
    @Test
    void forbiddenToReturnCreateBookPage() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("запрещает создавать новую книгу")
    @Test
    void forbiddenToCreateBook() throws Exception {
        mvc.perform(post("/book").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("запрещает удалять книгу")
    @Test
    void forbiddenToDeleteBook() throws Exception {
        mvc.perform(delete("/book/" + bookId).with(csrf()))
                .andExpect(status().isForbidden());
    }
}
