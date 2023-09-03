package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping ("/")
    public String viewBooks(Model model) {
        List<Book> bookList = bookService.getAll();
        model.addAttribute("books", bookList);
        return "viewBooks";
    }

    @GetMapping ("/book/{id}")
    public String viewBook(Model model, @PathVariable long id) {
        try {
            Book book = bookService.getById(id);
            model.addAttribute("book", book);
            return "viewBook";
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @GetMapping ("/edit")
    public String editBook(Model model, @RequestParam("id") long id) {
        try {
            Book book = bookService.getById(id);
            model.addAttribute("book", book);
            return "edit";
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }
}
