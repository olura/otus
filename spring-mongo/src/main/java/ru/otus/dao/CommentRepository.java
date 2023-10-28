package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
