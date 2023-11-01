package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByTitle(String title);
}
