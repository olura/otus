package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoORM implements AuthorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Author> getById(long id) {
        return Optional.of(entityManager.find(Author.class, id));
    }

    @Override
    @Transactional
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author insert(Author author) {
        return null;
    }

//    public Optional<Author> getById(long id) {
//        return Optional.of(jdbcOperations.queryForObject("SELECT id, name FROM Author WHERE id =:id",
//                Map.of("id", id), new BeanPropertyRowMapper<>(Author.class)));
//    }
//
//    public List<Author> getAll() {
//        return jdbcOperations.query("SELECT id, name FROM Author", new BeanPropertyRowMapper<>(Author.class));
//    }
//
//    public Author insert(Author author) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("author_name", author.getName());
//
//        jdbcOperations.update("INSERT INTO Author (name) SELECT :author_name " +
//                        "WHERE NOT EXISTS (SELECT 1 FROM Author WHERE name =:author_name)",
//                params, keyHolder, new String[]{"id"});
//        if (keyHolder.getKey() != null) {
//            author.setId(keyHolder.getKey().longValue());
//        } else {
//            author.setId(jdbcOperations.queryForObject("SELECT id FROM Author WHERE name =:author_name",
//                    params, Long.class));
//        }
//        return author;
//    }
}
