package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;
import ru.otus.exception.GenreExistExeption;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("from Genre", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre insert(Genre genre) throws GenreExistExeption {
        if (genre.getId() == 0) {
            try {
                entityManager.persist(genre);
                return genre;
            } catch (ConstraintViolationException e) {
                entityManager.clear();
                throw new GenreExistExeption("The author already exists");
            }
        }
        return entityManager.merge(genre);
    }
}
