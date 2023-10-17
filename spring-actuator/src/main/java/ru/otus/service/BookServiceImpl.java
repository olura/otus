package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.BookRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("The book with id " + id + " was not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book saveNewBook(BookDto bookDto) {
        Author author = authorRepository.findByName(bookDto.getAuthor());
        Genre genre = genreRepository.findByTitle(bookDto.getGenre());
        Book book = new Book(bookDto.getTitle(), author, genre);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book saveEditBook(BookDto bookDto, long id) {
        Author author = authorRepository.findByName(bookDto.getAuthor());
        Genre genre = genreRepository.findByTitle(bookDto.getGenre());
        Book book = new Book(id, bookDto.getTitle(), author, genre);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
