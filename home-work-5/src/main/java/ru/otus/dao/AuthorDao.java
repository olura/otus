package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

public interface AuthorDao {
    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getAll();

    Author insert(Author author) throws AuthorExistException;
}
