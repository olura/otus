package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс GenreDaoJdbcTest ")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {

    private final GenreDao genreDao;

    @Autowired
    public GenreDaoJdbcTest(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @DisplayName("возвращает ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(1,"Romance");
        Genre actualGenre = genreDao.getById(expectedGenre.getId()).get();
        assertEquals(expectedGenre, actualGenre);
    }

    @DisplayName("возвращает ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> genres = genreDao.getAll();
        assertEquals(3, genres.size());
    }

    @DisplayName("добавляет жанр в БД если такого жанра ещё нет")
    @Test
    void shouldInsertGenre() {
        int beforeSize =  genreDao.getAll().size();

        Genre expectedGenre = new Genre("Test_genre");
        Genre genre = genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.getById(genre.getId()).get();
        assertEquals(expectedGenre, actualGenre);

        int afterSize =  genreDao.getAll().size();
        assertEquals(beforeSize + 1, afterSize);

        genre = genreDao.insert(expectedGenre);
        actualGenre = genreDao.getById(genre.getId()).get();
        assertEquals(expectedGenre, actualGenre);

        int afterInsertDuplicateSize =  genreDao.getAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}
