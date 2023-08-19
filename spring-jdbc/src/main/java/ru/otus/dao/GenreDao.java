package ru.otus.dao;

import ru.otus.domain.Genre;
import ru.otus.exception.GenreExistExeption;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);

    Optional<Genre> getByTitle(String title);

    List<Genre> getAll();

    Genre insert(Genre genre) throws GenreExistExeption;
}
