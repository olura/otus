package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.config.SecurityConfiguration;
import ru.otus.service.CommentService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс CommentControllerTest пользователю с ролью USER ")
@WebMvcTest({CommentController.class, SecurityConfiguration.class})
@WithMockUser(roles = {"USER"})
public class CommentControllerSecurityUserTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    private final long bookId = 1;

    @DisplayName("возвращает страницу для создания нового комментария")
    @Test
    void createCommentPageShouldReturnCorrectView() throws Exception {
        mvc.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().isOk());
    }

    @DisplayName("позволяет создавать новый комментарий")
    @Test
    void createCommentShouldReturnCorrectView() throws Exception {
        mvc.perform(post("/book/" + bookId + "/comment").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
    @DisplayName("запрещает удалять комментарий")
    @Test
    void deleteBookShouldReturnCorrectView() throws Exception {
        mvc.perform(delete("/book/" + bookId + "/comment/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
