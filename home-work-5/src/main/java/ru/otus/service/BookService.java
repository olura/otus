package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getById(long id);

    List<Book> getAll();

    Book insert(String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption;

    void update(long id, String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption;

    void deleteById(long id);
}
