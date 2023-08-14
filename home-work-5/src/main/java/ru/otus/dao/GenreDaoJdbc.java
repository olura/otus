package ru.otus.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;
import ru.otus.exception.GenreExistExeption;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return jdbcOperations.query("SELECT id, title FROM Genre WHERE id =:id", Map.of("id", id),
                new BeanPropertyRowMapper<>(Genre.class)).stream().findAny();
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        return jdbcOperations.query("SELECT id, title FROM Genre WHERE title =:title",
                Map.of("title", title), new BeanPropertyRowMapper<>(Genre.class)).stream().findAny();
    }

    @Override
    public List<Genre> getAll() {
        return this.jdbcOperations.query("SELECT id, title FROM Genre", new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre insert(Genre genre) throws GenreExistExeption {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("genre_title", genre.getTitle());

        try {
            jdbcOperations.update("INSERT INTO Genre (title) VALUES (:genre_title)",
                    params, keyHolder, new String[]{"id"});
        } catch (DuplicateKeyException e) {
            throw new GenreExistExeption("The genre already exists");
        }
        genre.setId(keyHolder.getKey().longValue());

        return genre;
    }
}
