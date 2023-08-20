package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

public interface AuthorRepository {
    Optional<Author> getById(long id);

    List<Author> getAll();

    Author insert(Author author) throws AuthorExistException;
}
