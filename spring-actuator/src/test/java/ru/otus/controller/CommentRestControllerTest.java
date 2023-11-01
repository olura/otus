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
import ru.otus.dto.CommentDto;
import ru.otus.service.CommentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс CommentRestController ")
@WebMvcTest(CommentRestController.class)
public class CommentRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;


    @DisplayName("GET /api/book/1/comment возвращает HTTP-ответ со статусом 200 OK и списком комментраиев книги")
    @Test
    void getAllCommentShouldReturnAllCommentToBook() throws Exception {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);
        Comment comment1 = new Comment("Comment 1", book);
        Comment comment2 = new Comment("Comment 2", book);
        List<Comment> commentList = List.of(comment1, comment2);

        given(commentService.getAllCommentToBook(book.getId())).willReturn(commentList);

        mvc.perform(get("/api/book/1/comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @DisplayName("POST /api/book/1/comment возвращает HTTP-ответ со статусом 200 OK и сохранённый комментарий")
    @Test
    void createCommentShouldReturnValidComment() throws Exception {
        long bookId = 1;
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);
        Comment comment = new Comment("Test comment", book);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new CommentDto(comment));

        given(commentService.addComment(comment.getText(), comment.getBook().getId())).willReturn(comment);
        mvc.perform(post("/api/book/" + bookId + "/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test comment"));
    }

    @DisplayName("DELETE /api/book/1/comment/1 возвращает HTTP-ответ со статусом 200 OK и статус success")
    @Test
    void deleteCommentShouldReturnStateSuccess() throws Exception {
        long bookId = 1;

        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(commentService).deleteCommentById(valueCapture.capture());

        mvc.perform(delete("/api/book/" + bookId + "/comment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("success"));
        assertEquals(bookId, valueCapture.getValue());
    }
}
