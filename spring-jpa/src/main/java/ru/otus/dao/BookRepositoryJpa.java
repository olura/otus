package ru.otus.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Book> getById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-graph");
        Map<String, Object> properties = Map.of(FETCH.getKey(), entityGraph);
        return Optional.ofNullable(entityManager.find(Book.class, id, properties));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select distinct b from Book b left join fetch " +
                "b.author left join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) throws NoFoundBookException {
        entityManager.remove(getById(id).orElseThrow(
                () -> new NoFoundBookException("The book with id " + id + " was not found")));
    }
}
