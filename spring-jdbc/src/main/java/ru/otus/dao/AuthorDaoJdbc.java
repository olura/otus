package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.exception.AuthorExistException;

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

    @Override
    public Optional<Author> getById(long id) {
        return jdbcOperations.query("SELECT id, name FROM Author WHERE id =:id", Map.of("id", id),
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny();
    }

    @Override
    public Optional<Author> getByName(String name) {
        return jdbcOperations.query("SELECT id, name FROM Author WHERE name =:name", Map.of("name", name),
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny();
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("SELECT id, name FROM Author", new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public Author insert(Author author) throws AuthorExistException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("author_name", author.getName());

        try {
            jdbcOperations.update("INSERT INTO Author (name) VALUES (:author_name)",
                    params, keyHolder, new String[]{"id"});
        } catch (DuplicateKeyException e) {
            throw new AuthorExistException("The author already exists");
        }
        author.setId(keyHolder.getKey().longValue());

        return author;
    }
}
