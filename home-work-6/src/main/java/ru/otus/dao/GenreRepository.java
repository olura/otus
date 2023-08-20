package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Genre;
import ru.otus.exception.GenreExistExeption;

public interface GenreRepository {
    Optional<Genre> getById(long id);

    List<Genre> getAll();

    Genre insert(Genre genre) throws GenreExistExeption;
}
