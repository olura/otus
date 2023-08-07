package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;
import ru.otus.exception.GenreExistExeption;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @DisplayName("возвращает ожидаемый жанр по его названию")
    @Test
    void shouldReturnExpectedGenreByTitle() {
        Genre expectedGenre = new Genre(1,"Romance");
        Genre actualGenre = genreDao.getByTitle(expectedGenre.getTitle()).get();
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
    void shouldInsertGenre() throws GenreExistExeption {
        int beforeSize =  genreDao.getAll().size();

        Genre expectedGenre = new Genre("Test_genre");
        Genre genre = genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.getById(genre.getId()).get();
        assertEquals(expectedGenre, actualGenre);

        int afterSize =  genreDao.getAll().size();
        assertEquals(beforeSize + 1, afterSize);

        assertThrows(GenreExistExeption.class, () -> genreDao.insert(expectedGenre));

        int afterInsertDuplicateSize =  genreDao.getAll().size();
        assertEquals(afterSize, afterInsertDuplicateSize);
    }
}
