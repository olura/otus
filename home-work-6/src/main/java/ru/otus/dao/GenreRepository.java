package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Genre;

public interface GenreRepository {

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    Genre save(Genre genre);
}
