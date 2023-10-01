package ru.otus.service;

import ru.otus.domain.Comment;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentToBook(String id);

    Comment addComment(String text, String bookId) throws BookNotFoundException;

    void deleteCommentById(String id);
}
