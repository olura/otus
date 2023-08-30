package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook_id(Long book);
}
