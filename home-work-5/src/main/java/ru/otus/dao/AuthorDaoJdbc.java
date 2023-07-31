package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Optional<Author> getById(long id) {
        return Optional.of(jdbcOperations.queryForObject("SELECT id, name FROM Author WHERE id =:id",
                Map.of("id", id), new BeanPropertyRowMapper<>(Author.class)));
    }

    public List<Author> getAll() {
        return jdbcOperations.query("SELECT id, name FROM Author", new BeanPropertyRowMapper<>(Author.class));
    }
}
