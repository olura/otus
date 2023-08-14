package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    @Transactional
    public Author insert(Author author) {
        entityManager.persist(author);
        return entityManager.find(Author.class, author.getId());
    }
}
