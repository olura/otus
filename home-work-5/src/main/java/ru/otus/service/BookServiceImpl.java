package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book insert(String title, long authorId, long genreId) {
        Author author = null;
        Genre genre = null;
        try {
            author = authorDao.getById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException("This author does not exist"));
            genre = genreDao.getById(genreId)
                    .orElseThrow(() -> new GenreNotFoundExeption("This genre does not exist"));
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
            System.out.println("Book does inserted. Error: " + e.getMessage());
        }
        Book book = new Book(title, author, genre);
        return bookDao.insert(book);
    }

    @Override
    public void update(long id, String title, long authorId, long genreId) {
        Author author = null;
        Genre genre = null;
        try {
            author = authorDao.getById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException("This author does not exist"));
            genre = genreDao.getById(genreId)
                    .orElseThrow(() -> new GenreNotFoundExeption("This genre does not exist"));
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
            System.out.println("Book does update. Error: " + e.getMessage());
        }
        Book book = new Book(id, title, author, genre);
        bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
