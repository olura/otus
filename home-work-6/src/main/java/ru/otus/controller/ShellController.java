package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.BookService;
import ru.otus.service.ConverterService;

import java.util.List;

@ShellComponent
public class ShellController {

    private final BookService bookService;

    private final ConverterService converterService;

    @Autowired
    public ShellController(BookService bookService, ConverterService converterService) {
        this.bookService = bookService;
        this.converterService = converterService;
    }

    @ShellMethod(key = {"f", "find"}, value = "Find book by id")
    public String getById (@ShellOption long id) {
        Book book;
        try {
            book = bookService.getById(id);
        } catch (BookNotFoundException e) {
            return e.getMessage();
        }
        return converterService.convertListBooksToString(List.of(book));
    }

    @ShellMethod(key = {"a", "all"}, value = "Show all book")
    public String getAllBook() {
        List<Book> books = bookService.getAll();
        return converterService.convertListBooksToString(books);
    }

    @ShellMethod(key = {"i", "insert"}, value = "Insert book")
    public String insert(@ShellOption String title, @ShellOption long authorId, @ShellOption long genreId) {
        try {
            bookService.insert(title, authorId, genreId);
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
            return "The book does not inserted. Error: " + e.getMessage();
        }
        return "The book insert was successful";
    }

    @ShellMethod(key = {"u", "update"}, value = "Update book")
    public String update(@ShellOption long id, @ShellOption String title,
                         @ShellOption long authorId, @ShellOption long genreId) {
        try {
            bookService.update(id, title, authorId, genreId);
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
            return "The book does not update. Error: " + e.getMessage();
        }
        return "The book update was successful";
    }

    @ShellMethod(key = {"d", "delete"}, value = "Delete book by id")
    public String deleteById(@ShellOption long id) {
        try {
            bookService.deleteById(id);
        } catch (BookNotFoundException e) {
            return "The book does not delete. Error: " + e.getMessage();
        }
        return "The book delete was successful";
    }

    @ShellMethod(key = {"ac", "add comment"}, value = "Add comment")
    public String addComment(@ShellOption String text, @ShellOption long bookId) {
        Comment comment;
        try {
            comment = bookService.addComment(text, bookId);
        } catch (BookNotFoundException e) {
            return "Comment does not inserted. Error: " + e.getMessage();
        }
        return "The comment added was successful to book: " + comment.getBook().getTitle();
    }

    @ShellMethod(key = {"gc", "get comments"}, value = "Get comments")
    public String addComment(@ShellOption long bookId) {
        List<Comment> comment = bookService.getAllCommentToBook(bookId);
        if (comment.isEmpty()) {
            return "No comments found";
        }
        return converterService.convertListCommentToString(comment);
    }

    @ShellMethod(key = {"dc", "delete comment"}, value = "Delete comment by id")
    public String deleteCommentById(@ShellOption long id) {
        try {
            bookService.deleteCommentById(id);
        } catch (BookNotFoundException e) {
            return "The comment does not delete. Error: " + e.getMessage();
        }
        return "The comment delete was successful";
    }
}