package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Author;

public interface AuthorDao {
    Optional<Author> getByName(String name);

    List<Author> getAll();

    Author insert(Author author);
}
