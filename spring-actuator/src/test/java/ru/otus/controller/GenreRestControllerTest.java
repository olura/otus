package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Genre;
import ru.otus.service.GenreServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс GenreRestController ")
@WebMvcTest(GenreRestController.class)
public class GenreRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreServiceImpl genreService;

    @DisplayName("GET /api/genre возвращает HTTP-ответ со статусом 200 OK и списком авторов")
    @Test()
    void getAllAuthorShouldReturnAllAuthor() throws Exception {
        Genre genre = new Genre(1, "Test_genre");
        Genre genre1 = new Genre(2, "Test_genre1");

        List<Genre> genreList = List.of(genre, genre1);

        given(genreService.getAll()).willReturn(genreList);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
}
