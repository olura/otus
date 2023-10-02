package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.CommentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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

    @DisplayName("возвращает страницу для создания нового коментария")
    @Test
    void createCommentPageShouldReturnCorrectView() throws Exception {
        long bookId = 1;
        mvc.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", bookId));
    }

    @DisplayName("создаёт новый коментарий")
    @Test
    void createCommentShouldReturnCorrectView() throws Exception {
        long bookId = 1;
        Author author = new Author("1","Test_author");
        Genre genre = new Genre("1", "Test_genre");
        Book book = new Book("1","Test book", author, genre);
        Comment comment = new Comment("Test comment", book);

        given(commentService.addComment(comment.getText(), comment.getBook().getId())).willReturn(comment);
        mvc.perform(post("/book/" + bookId + "/comment"))
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("удаляет коментарий")
    @Test
    void deleteBookShouldReturnCorrectView() throws Exception {
        long bookId = 1;

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(commentService).deleteCommentById(valueCapture.capture());

        mvc.perform(delete("/book/" + bookId + "/comment/1"))
                .andExpect(status().is3xxRedirection());
        assertEquals(bookId, valueCapture.getValue());
    }
}
