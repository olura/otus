package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    Book getById(long id) throws BookNotFoundException;

    List<Book> getAll();

    Book save(BookDto bookDto);

    void deleteById(long id);
}
