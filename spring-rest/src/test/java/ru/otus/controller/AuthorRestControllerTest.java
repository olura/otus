package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.service.AuthorServiceImpl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс AuthorRestController ")
@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @DisplayName("GET /api/author возвращает HTTP-ответ со статусом 200 OK и списком авторов")
    @Test()
    void getAllAuthorShouldReturnAllAuthor() throws Exception {
        Author author = new Author(1,"Test_author");
        Author author1 = new Author(2,"Test_author1");
        List<Author> authorList = List.of(author, author1);

        given(authorService.getAll()).willReturn(authorList);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
}
