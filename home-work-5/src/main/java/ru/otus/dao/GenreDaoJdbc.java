package ru.otus.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Optional<Genre> getById(long id) {
        return Optional.of((Genre)this.jdbcOperations.queryForObject("SELECT id, title FROM Genre WHERE id =:id",
                Map.of("id", id), new BeanPropertyRowMapper<>(Genre.class)));
    }

    public List<Genre> getAll() {
        return this.jdbcOperations.query("SELECT id, title FROM Genre", new BeanPropertyRowMapper<>(Genre.class));
    }
}
