package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс BookPagesController ")
@WebMvcTest(BookPagesController.class)
public class BookPagesControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("GET / возвращает HTTP-ответ со статусом 200 OK")
    @Test
    void booksPageShouldReturnCorrectView() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
