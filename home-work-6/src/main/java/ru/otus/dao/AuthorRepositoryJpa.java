package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

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
        TypedQuery<Author> query = entityManager.createQuery("from Author", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Author insert(Author author) throws AuthorExistException {
        if (author.getId() == 0) {
            try {
                entityManager.persist(author);
                return author;
            } catch (ConstraintViolationException e) {
                entityManager.clear();
                throw new AuthorExistException("The author already exists");
            }
        }
        return entityManager.merge(author);
    }
}
