package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public List<BookDto> getAllBook() {
        return bookService.getAll()
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping ("/api/book/{id}")
    public BookDto getBook(@PathVariable long id) throws BookNotFoundException {
        Book book = bookService.getById(id);
        return new BookDto(book);
    }

    @PostMapping("/api/book")
    public BookDto createBook(@RequestBody BookDto book) {
        if ("".equals(book.getAuthor()) || "".equals(book.getGenre())) {
            throw new RuntimeException("the author or genre of the book is not specified");
        }
        Book savedBook = bookService.saveNewBook(book);
        return new BookDto(savedBook);
    }

    @PutMapping("/api/book/{id}")
    public BookDto editBook(@PathVariable("id") long id, @RequestBody BookDto book) {
        if ("".equals(book.getAuthor()) || "".equals(book.getGenre())) {
            throw new RuntimeException("the author or genre of the book is not specified");
        }
        Book savedBook = bookService.saveEditBook(book, id);
        return new BookDto(savedBook);
    }

    @DeleteMapping("/api/book/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "{\"state\":\"success\"}";
    }

    @ExceptionHandler({BookNotFoundException.class, RuntimeException.class})
    public String handleMyException(Exception ex) {
        return "{\"state\":\"fail\"," +
                "\"message\":\"" + ex.getMessage() + "\"}";
    }
}
