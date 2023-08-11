package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getById(long id);

    List<Book> getAll();

    Book insert(String title, long authorId, long genreId);

    void update(long id, String title, long authorId, long genreId);

    void deleteById(long id);
}
