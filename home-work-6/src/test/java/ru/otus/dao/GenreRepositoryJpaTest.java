package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс GenreRepositoryJpa ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(1,"Romance");
        Optional<Genre> actualGenre = genreRepository.getById(expectedGenre.getId());
        assertEquals(Optional.of(expectedGenre), actualGenre);
    }

    @DisplayName("возвращает ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> genres = genreRepository.getAll();
        assertEquals(3, genres.size());
    }

    @DisplayName("сохраняет жанр в БД")
    @Test
    void shouldInsertGenre() {
        int beforeSize = genreRepository.getAll().size();
        Genre expectedGenre = new Genre("Test_genre");
        Genre genre = genreRepository.save(expectedGenre);
        Genre actualGenre = entityManager.find(Genre.class, genre.getId());
        assertEquals(expectedGenre, actualGenre);
        int afterSize =  genreRepository.getAll().size();
        assertEquals(beforeSize + 1, afterSize);

        expectedGenre.setTitle("New genre");
        Genre genre1 = genreRepository.save(expectedGenre);
        Genre actualGenre1 = entityManager.find(Genre.class, genre1.getId());
        assertEquals(expectedGenre, actualGenre);
        int afterInsertDuplicateSize =  genreRepository.getAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}
