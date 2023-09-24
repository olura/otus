package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("from Author", Author.class);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            entityManager.persist(author);
            return author;
        }
        return entityManager.merge(author);
    }
}
