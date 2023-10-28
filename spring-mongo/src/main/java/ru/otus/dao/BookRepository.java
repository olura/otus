package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, String> {

    Optional<Book> findById(String id);

    List<Book> findAll();
}
