package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

import java.util.List;

@RestController
public class GenreRestController {

    private final GenreService genreService;

    @Autowired
    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public List<Genre> getAllGenre() {
        return genreService.getAll();
    }
}
