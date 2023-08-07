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
import ru.otus.exception.AuthorExistException;
import ru.otus.exception.GenreExistExeption;

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
        return jdbcOperations.query(
                "SELECT b.id, b.title, b.author_id, a.name, b.genre_id, g.title g_title " +
                        "FROM Book b JOIN Author a ON b.author_id=a.id JOIN Genre g ON b.genre_id=g.id WHERE b.id =:id",
                Map.of("id", id), new BookMapper()).stream().findAny();
    }

    public List<Book> getAll() {
        return jdbcOperations.query("SELECT b.id, b.title, b.author_id, a.name, b.genre_id, g.title g_title " +
                "FROM Book b, Author a, Genre g WHERE b.author_id=a.id and b.genre_id=g.id", new BookMapper());
    }

    public Book insert(Book book) {
        Author author;
        Genre genre;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();

        try {
            author = authorDao.insert(book.getAuthor());
        } catch (AuthorExistException e) {
            author = authorDao.getByName(book.getAuthor().getName()).get();
        }
        try {
            genre = genreDao.insert(book.getGenre());
        } catch (GenreExistExeption e) {
            genre = genreDao.getByTitle(book.getGenre().getTitle()).get();
        }
        params.addValue("title", book.getTitle());
        params.addValue("author_id", author.getId());
        params.addValue("genre_id", genre.getId());

        jdbcOperations.update("INSERT INTO Book (title, author_id, genre_id) " +
                        "VALUES (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    public void update(Book book) {
        Author author = null;
        Genre genre = null;

        try {
            author = authorDao.insert(book.getAuthor());
            genre = genreDao.insert(book.getGenre());
        } catch (AuthorExistException e) {
            author = authorDao.getByName(book.getAuthor().getName()).get();
        } catch (GenreExistExeption e) {
            genre = genreDao.getByTitle(book.getGenre().getTitle()).get();
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", author.getId());
        params.addValue("genre_id", genre.getId());

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
