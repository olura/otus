package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, String> {

    List<Author> findAll();

    Author findByName(String name);
}
