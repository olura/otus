package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @Autowired
    public BookRestController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/api/book")
    public List<BookDto> getAllBook() {
        return bookService.getAll()
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping ("/api/book/{id}")
    public BookDto getBook(@PathVariable long id) {
        try {
            Book book = bookService.getById(id);
            return new BookDto(book);
        } catch (BookNotFoundException e) {
            return null;
        }
    }

    @PostMapping("/api/book")
    public BookDto createBook(@RequestBody BookDto book) {
        Book savedBook = bookService.saveNewBook(book);
        return new BookDto(savedBook);
    }

    @PutMapping("/api/book/{id}")
    public BookDto editBook(@PathVariable("id") long id, @RequestBody BookDto book) {
        Book savedBook = bookService.saveEditBook(book, id);
        return new BookDto(savedBook);
    }

    @DeleteMapping("/api/book/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "{\"state\":\"success\"}";
    }

    @GetMapping("/api/book/{book_id}/comment")
    public List<CommentDto> getAllComment(@PathVariable("book_id") long bookId) {
        return commentService.getAllCommentToBook(bookId)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/book/{book_id}/comment")
    public CommentDto createComment(@PathVariable("book_id") long bookId, @RequestBody CommentDto comment) {
        try {
            Comment savedComment = commentService.addComment(comment.getText(), bookId);
            return new CommentDto(savedComment);
        } catch (BookNotFoundException e) {
            return null;
        }
    }

    @DeleteMapping("/api/book/{book_id}/comment/{id}")
    public String deleteComment(@PathVariable("book_id") long bookId, @PathVariable("id") long commentId) {
        commentService.deleteCommentById(commentId);
        return "{\"state\":\"success\"}";
    }
}
