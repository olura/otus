package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);
}
