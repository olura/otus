package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.config.SecurityConfiguration;
import ru.otus.service.CommentService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс CommentControllerTest ")
@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = {SecurityConfiguration.class})
public class CommentControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    private final long bookId = 1;
    @DisplayName("неавторизированному пользователю не возвращает страницу для создания нового коментария")
    @Test
    void forbiddenCreateCommentPage() throws Exception {
        mvc.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированному пользователю запрещено создавать новый коментарий")
    @Test
    void forbiddenCreateComment() throws Exception {
        mvc.perform(post("/book/" + bookId + "/comment").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("неавторизированному пользователю запрещено удалять коментарий")
    @Test
    void forbiddenDeleteBook() throws Exception {
        mvc.perform(delete("/book/" + bookId + "/comment/1").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
