package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    private final CommentService commentService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, CommentService commentService,
                          AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String booksPage(Model model) {
        List<Book> bookList = bookService.getAll();
        model.addAttribute("books", bookList);
        return "viewBooks";
    }

    @GetMapping ("/book/{id}")
    public String bookPage(Model model, @PathVariable long id) {
        try {
            Book book = bookService.getById(id);
            List<Comment> commentList = commentService.getAllCommentToBook(id);
            model.addAttribute("book", book);
            model.addAttribute("comments", commentList);
            return "viewBook";
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @GetMapping ("/edit")
    public String editPage(Model model, @RequestParam("id") long id) {
        try {
            Book book = bookService.getById(id);
            List<Author> authorList = authorService.getAllAuthors();
            List<Genre> genreList = genreService.getAllGenre();
            model.addAttribute("book", new BookDto(book));
            model.addAttribute("authors", authorList);
            model.addAttribute("genres", genreList);
            return "edit";
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @PutMapping("/edit")
    public String saveBook(BookDto book) {
        bookService.save(book);
        return "redirect:/book/" + book.getId();
    }

    @GetMapping ("/book")
    public String createBookPage(Model model) {
        List<Author> authorList = authorService.getAllAuthors();
        List<Genre> genreList = genreService.getAllGenre();
        model.addAttribute("authors", authorList);
        model.addAttribute("genres", genreList);
        return "createBook";
    }

    @PostMapping("/book")
    public String createBook(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
