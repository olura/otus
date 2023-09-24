package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    Book getById(long id) throws BookNotFoundException;

    List<Book> getAll();

    Book insert(String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption;

    Book update(long id, String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption;

    void deleteById(long id) throws BookNotFoundException;

    List<Comment> getAllCommentToBook(long id);

    Comment addComment(String text, long bookId) throws BookNotFoundException;

    void deleteCommentById(long id) throws BookNotFoundException;
}
