package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Author insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_name", author.getName());

        jdbcOperations.update("INSERT INTO Author (name) SELECT :author_name " +
                        "WHERE NOT EXISTS (SELECT 1 FROM Author WHERE name =:author_name)",
                params, keyHolder, new String[]{"id"});
        if (keyHolder.getKey() != null) {
            author.setId(keyHolder.getKey().longValue());
        } else {
            author.setId(jdbcOperations.queryForObject("SELECT id FROM Author WHERE name =:author_name",
                    params, Long.class));
        }
        return author;
    }
}
