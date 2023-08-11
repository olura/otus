package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Genre;
import ru.otus.exception.GenreNotFoundExeption;

public interface GenreDao {
    Optional<Genre> getById(long id);

    Optional<Genre> getByTitle(String title);

    List<Genre> getAll();

    Genre insert(Genre genre) throws GenreNotFoundExeption;
}
