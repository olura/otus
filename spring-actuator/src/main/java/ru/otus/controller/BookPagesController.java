package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookPagesController {

    @GetMapping("/")
    public String listBooksPage() {
        return "list";
    }

    @GetMapping("/add")
    public String addBookPage() {
        return "add";
    }

    @GetMapping("/book/{book_id}/comment")
    public String createCommentPage(Model model, @PathVariable("book_id") long bookId) {
        model.addAttribute("id", bookId);
        return "createComment";
    }

    @GetMapping ("/book/{book_id}")
    public String bookPage(Model model, @PathVariable("book_id") long bookId) {
        model.addAttribute("id", bookId);
        return "viewBook";
    }

    @GetMapping ("/edit")
    public String editPage(Model model, @RequestParam("id") long bookId) {
        model.addAttribute("id", bookId);
        return "edit";
    }
}
