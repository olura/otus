package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

import java.util.List;

@Controller
//@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping ("/")
    public String viewBook(Model model) {
        List<Book> bookList = bookService.getAll();
        model.addAttribute("books", bookList);
        return "viewBook";
    }
}
