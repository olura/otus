package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Book> getById(long id) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b left join fetch " +
                "b.author left join fetch b.genre where b.id=:id", Book.class);
        query.setParameter("id", id);
        return query.getResultStream().findAny();
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
