package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Book;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundExeption;
import ru.otus.exception.NoFoundBookException;
import ru.otus.service.BookService;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

@ShellComponent
public class ShellController {

    private final BookService bookService;

    @Autowired
    public ShellController(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = {"f", "find"}, value = "Find book by id")
    public String getById (@ShellOption Long id) {
        Book book;
        try {
            book = bookService.getById(id).orElseThrow(
                    () -> new NoFoundBookException("The book was not found"));
        } catch (NoFoundBookException e) {
            return e.getMessage();
        }
        return convertListBooksToString(Arrays.asList(book));
    }

    @ShellMethod(key = {"a", "all"}, value = "Show all book")
    public String getAllBook() {
        List<Book> books = bookService.getAll();
        return convertListBooksToString(books);
    }

    @ShellMethod(key = {"i", "insert"}, value = "Insert book")
    public String insert(@ShellOption String title, @ShellOption int author_id, @ShellOption int genre_id) {
        try {
            bookService.insert(title, author_id, genre_id);
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
            return "Book does inserted. Error: " + e.getMessage();
        }
        return "The book insert was successful";
    }

    @ShellMethod(key = {"u", "update"}, value = "Update book")
    public String update(@ShellOption long id, @ShellOption String title,
                         @ShellOption int author_id, @ShellOption int genre_id) {
        try {
            bookService.update(id, title, author_id, genre_id);
        } catch (AuthorNotFoundException | GenreNotFoundExeption e) {
             return "Book does update. Error: " + e.getMessage();
        }
        return "The book update was successful";
    }

    @ShellMethod(key = {"d", "delete"}, value = "Delete book by id")
    public String deleteById(@ShellOption long id) {
        bookService.deleteById(id);
        return "The book delete was successful";
    }

    private String convertListBooksToString(List<Book> books) {
        final String ansiReset = "\u001B[0m";
        final String ansiBold = "\u001B[1m";
        final String ansiBlack = "\u001B[30m";
        final String ansiUnderlined = "\u001B[4m";

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("|%s%s%-30s|%-20s|%-10s%s|\n",
                ansiUnderlined, ansiBold, "Title", "Author", "Genre", ansiReset);

        for (Book book: books) {
            formatter.format("%s|%-30s|%-20s|%-10s|%s\n",
                    ansiBlack, book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle(), ansiReset);
        }
        return sb.toString();
    }
}
