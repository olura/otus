package ru.otus.service;

import ru.otus.domain.Comment;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentToBook(long id);

    Comment addComment(String text, long bookId) throws BookNotFoundException;

    void deleteCommentById(long id);
}
