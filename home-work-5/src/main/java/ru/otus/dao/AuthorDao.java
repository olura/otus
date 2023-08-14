package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getAll();

    Author insert(Author author) throws AuthorExistException;
}
