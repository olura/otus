package ru.otus.dao;

import ru.otus.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> getById(long id);

    List<Comment> getAllCommentToBook(long id);

    Comment insert(Comment comment);

    void update(Comment comment);

    void deleteById(long id);
}
