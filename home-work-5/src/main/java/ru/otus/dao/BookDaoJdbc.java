package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbcOperations = jdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public Optional<Book> getById(long id) {
        return Optional.of(jdbcOperations.queryForObject(
                "SELECT b.id, b.title, b.author_id, a.name, b.genre_id, g.title g_title " +
                    "FROM Book b, Author a, Genre g WHERE b.author_id=a.id and b.genre_id=g.id and b.id =:id",
                Map.of("id", id), new BookMapper()));
    }

    public List<Book> getAll() {
        return jdbcOperations.query("SELECT b.id, b.title, b.author_id, a.name, b.genre_id, g.title g_title " +
                "FROM Book b, Author a, Genre g WHERE b.author_id=a.id and b.genre_id=g.id", new BookMapper());
    }

    public Book insert(Book book) {
        long authorId = authorDao.insert(book.getAuthor()).getId();
        long genreId = genreDao.insert(book.getGenre()).getId();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", authorId);
        params.addValue("genre_id", genreId);

        jdbcOperations.update("INSERT INTO Book (title, author_id, genre_id) " +
                        "VALUES (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    public void update(Book book) {
        long authorId = authorDao.insert(book.getAuthor()).getId();
        long genreId = genreDao.insert(book.getGenre()).getId();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", authorId);
        params.addValue("genre_id", genreId);

        jdbcOperations.update("UPDATE Book SET title=:title, author_id=:author_id, genre_id=:genre_id WHERE id=:id",
                params);
    }

    public void deleteById(long id) {
        this.jdbcOperations.update("DELETE FROM Book WHERE id = :id", Map.of("id", id));
    }

    static class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getInt("id");
            String title = rs.getString("title");
            int authorId = rs.getInt("author_id");
            String authorName = rs.getString("name");
            Author author = new Author(authorId, authorName);
            int genreId = rs.getInt("genre_id");
            String genreTitle = rs.getString("g_title");
            Genre genre = new Genre(genreId, genreTitle);
            return new Book(id, title, author, genre);
        }
    }
}
