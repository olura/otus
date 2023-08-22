package ru.otus.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraphAuthor = entityManager.getEntityGraph("author-entity-graph");
        EntityGraph<?> entityGraphGenre = entityManager.getEntityGraph("genre-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select distinct b from Book b join fetch " +
                "b.author join fetch b.genre", Book.class);
        query.setHint(FETCH.getKey(), entityGraphAuthor);
        query.setHint(FETCH.getKey(), entityGraphGenre);
        return query.getResultList();
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) throws NoFoundBookException {
        entityManager.remove(getById(id).orElseThrow(
                () -> new NoFoundBookException("The book with id " + id + " was not found")));
    }
}
