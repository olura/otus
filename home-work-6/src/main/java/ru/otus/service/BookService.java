package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getById(long id);

    List<Book> getAll();

    Book insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
