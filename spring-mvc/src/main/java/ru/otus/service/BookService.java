package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    Book getById(long id) throws BookNotFoundException;

    List<Book> getAll();

    Book save(BookDto bookDto);

    void deleteById(long id);

    List<Comment> getAllCommentToBook(long id);

    Comment addComment(String text, long bookId) throws BookNotFoundException;

    void deleteCommentById(long id);

    List<Author> getAllAuthors();

    List<Genre> getAllGenre();

}
