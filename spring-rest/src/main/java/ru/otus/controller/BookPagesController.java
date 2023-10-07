package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPagesController {

    @GetMapping("/")
    public String listBooksPage(Model model) {
        return "list";
    }

    @GetMapping("/add")
    public String addBooksPage(Model model) {
        return "add";
    }
}
