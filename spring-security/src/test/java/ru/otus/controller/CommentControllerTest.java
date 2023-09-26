package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.CommentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс CommentControllerTest ")
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    private final long bookId = 1;

    @DisplayName("возвращает страницу для создания нового коментария")
    @Test
    @WithMockUser(username = "user")
    void createCommentPageShouldReturnCorrectView() throws Exception {
        long bookId = 1;
        mvc.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", bookId));
    }

    @DisplayName("создаёт новый коментарий")
    @Test
    @WithMockUser(username = "user")
    void createCommentShouldReturnCorrectView() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);
        Comment comment = new Comment("Test comment", book);

        given(commentService.addComment(comment.getText(), comment.getBook().getId())).willReturn(comment);
        mvc.perform(post("/book/" + bookId + "/comment").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("удаляет коментарий")
    @Test
    @WithMockUser(username = "user")
    void deleteBookShouldReturnCorrectView() throws Exception {
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(commentService).deleteCommentById(valueCapture.capture());

        mvc.perform(delete("/book/" + bookId + "/comment/1").with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(bookId, valueCapture.getValue());
    }

    @DisplayName("неавторизированному пользователю не возвращает страницу для создания нового коментария")
    @Test
    void forbiddenCreateCommentPage() throws Exception {
        mvc.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("неавторизированному пользователю запрещено создавать новый коментарий")
    @Test
    void forbiddenCreateComment() throws Exception {
        mvc.perform(post("/book/" + bookId + "/comment").with(csrf()))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("неавторизированному пользователю запрещено удалять коментарий")
    @Test
    void forbiddenDeleteBook() throws Exception {
        mvc.perform(delete("/book/" + bookId + "/comment/1").with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
