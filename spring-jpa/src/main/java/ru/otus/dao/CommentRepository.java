package ru.otus.dao;

import ru.otus.domain.Comment;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> getById(long id);

    List<Comment> getAllCommentToBook(long id);

    Comment save(Comment comment);

    void deleteById(long id) throws NoFoundBookException;
}
