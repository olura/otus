package ru.otus.dao;

import ru.otus.domain.Book;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> getById(long id);

    List<Book> getAll();

    Book save(Book book);

    void deleteById(long id) throws NoFoundBookException;
}
