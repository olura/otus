package ru.otus.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, String> {

    Genre findByTitle(String title);

    List<Genre> findAll();
}
