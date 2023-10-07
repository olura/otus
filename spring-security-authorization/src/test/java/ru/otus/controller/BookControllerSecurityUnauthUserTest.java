package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.config.SecurityConfiguration;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс BookControllerTest ")
@WebMvcTest({BookController.class, SecurityConfiguration.class})
public class BookControllerSecurityUnauthUserTest {

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


    @DisplayName("неавторизированным пользователям разрешает доступ к странице логина")
    @Test
    void givenAccessToLoginPage() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
    @DisplayName("неавторизированным пользователям запрещает доступ к корневой странице")
    @Test
    void forbiddenBooksPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает доступ к странице книги")
    @Test
    void forbiddenBookPage() throws Exception {
        mvc.perform(get("/book/" + bookId))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает доступ к странице для редактирования книги")
    @Test
    void forbiddenBookEditPage() throws Exception {
        mvc.perform(get("/edit?id=" + bookId))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает сохранять книгу")
    @Test
    void forbiddenToSaveBook() throws Exception {
        mvc.perform(put( "/edit").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает возвращать страницу для создания новой книги")
    @Test
    void forbiddenToReturnCreateBookPage() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает создавать новую книгу")
    @Test
    void forbiddenToCreateBook() throws Exception {
        mvc.perform(post("/book").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("неавторизированным пользователям запрещает удалять книгу")
    @Test
    void forbiddenToDeleteBook() throws Exception {
        mvc.perform(delete("/book/" + bookId).with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
