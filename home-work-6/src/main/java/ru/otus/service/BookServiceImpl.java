package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(long id) {
        return bookRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public Book insert(String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption {
        Author author = authorRepository.getById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("This author does not exist"));
        Genre genre = genreRepository.getById(genreId)
                .orElseThrow(() -> new GenreNotFoundExeption("This genre does not exist"));
        Book book = new Book(title, author, genre);
        return bookRepository.insert(book);
    }

    @Override
    @Transactional
    public void update(long id, String title, long authorId, long genreId)
            throws AuthorNotFoundException, GenreNotFoundExeption {
        Author author = authorRepository.getById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("This author does not exist"));
        Genre genre = genreRepository.getById(genreId)
                .orElseThrow(() -> new GenreNotFoundExeption("This genre does not exist"));
        Book book = new Book(id, title, author, genre);
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllComment(long id) {
        return commentRepository.getAllCommentToBook(id);
    }
}
