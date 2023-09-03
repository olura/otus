package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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
            List<Comment> commentList = bookService.getAllCommentToBook(id);
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
            List<Author> authorList = bookService.getAllAuthors();
            List<Genre> genreList = bookService.getAllGenre();
            model.addAttribute("book", new BookDto(book));
            model.addAttribute("authors", authorList);
            model.addAttribute("genres", genreList);
            return "edit";
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @PatchMapping("/edit")
    public String saveBook(BookDto book) {
        bookService.save(book);
        return "redirect:/book/" + book.getId();
    }

    @GetMapping ("/book")
    public String createBookPage(Model model) {
        List<Author> authorList = bookService.getAllAuthors();
        List<Genre> genreList = bookService.getAllGenre();
        model.addAttribute("authors", authorList);
        model.addAttribute("genres", genreList);
        return "createBook";
    }

    @PostMapping("/book")
    public String createBook(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping ("/book/{book_id}/comment")
    public String createCommentPage(Model model, @PathVariable("book_id") long bookId) {
        model.addAttribute("id", bookId);
        return "createComment";
    }

    @PostMapping("/book/{book_id}/comment")
    public String createComment(@PathVariable("book_id") long bookId, String comment) {
        try {
            bookService.addComment(comment, bookId);
            System.out.println(comment);
            return "redirect:/book/" + bookId;
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @DeleteMapping("/book/{book_id}/comment/{id}")
    public String deleteComment(@PathVariable("book_id") long bookId, @PathVariable("id") long commentId) {
        bookService.deleteCommentById(commentId);
        return "redirect:/book/" + bookId;
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
