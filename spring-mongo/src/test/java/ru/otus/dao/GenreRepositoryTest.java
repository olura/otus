package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс GenreRepositoryJpa ")
@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращает ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre("1","Romance");
        Optional<Genre> actualGenre = genreRepository.findById(expectedGenre.getId());
        assertEquals(Optional.of(expectedGenre), actualGenre);
    }

    @DisplayName("возвращает ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> genres = genreRepository.findAll();
        assertEquals(3, genres.size());
    }

    @DisplayName("сохраняет жанр в БД")
    @Test
    void shouldInsertGenre() {
        int beforeSize = genreRepository.findAll().size();
        Genre expectedGenre = new Genre("Test_genre");
        Genre genre = genreRepository.save(expectedGenre);
        Genre actualGenre = genreRepository.findById(genre.getId()).get();
        assertEquals(expectedGenre, actualGenre);
        int afterSize = genreRepository.findAll().size();
        assertEquals(beforeSize + 1, afterSize);

        expectedGenre.setTitle("New genre");
        Genre genre1 = genreRepository.save(expectedGenre);
        Genre actualGenre1 = genreRepository.findById(genre1.getId()).get();
        assertEquals(expectedGenre, actualGenre1);
        int afterInsertDuplicateSize = genreRepository.findAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);

        genreRepository.deleteById(genre.getId());
    }
}
