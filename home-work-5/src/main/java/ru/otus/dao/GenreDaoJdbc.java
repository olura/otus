package ru.otus.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Optional<Genre> getByName(String title) {
        return Optional.of((Genre)this.jdbcOperations.queryForObject("SELECT id, title FROM Genre WHERE title =:title",
                Map.of("title", title), new BeanPropertyRowMapper<>(Genre.class)));
    }

    public List<Genre> getAll() {
        return this.jdbcOperations.query("SELECT id, title FROM Genre", new BeanPropertyRowMapper<>(Genre.class));
    }

    public Genre insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_title", genre.getTitle());

        jdbcOperations.update("INSERT INTO Genre (title) SELECT :genre_title " +
                        "WHERE NOT EXISTS (SELECT 1 FROM Genre WHERE title =:genre_title)",
                params, keyHolder, new String[]{"id"});
        if (keyHolder.getKey() != null) {
            genre.setId(keyHolder.getKey().longValue());
        } else {
            genre.setId(jdbcOperations.queryForObject("SELECT id FROM Genre WHERE title =:genre_title",
                    params, Long.class));
        }
        return genre;
    }
}
