package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CommentRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAllCommentToBook(long id) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book.id = :id",
                Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(long id) throws NoFoundBookException {
        entityManager.remove(getById(id).orElseThrow(
                () -> new NoFoundBookException("The comment with id " + id + " was not found")));
    }
}
