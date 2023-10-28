package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.exception.BookNotFoundException;
import ru.otus.service.CommentService;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/book/{book_id}/comment")
    public String createCommentPage(Model model, @PathVariable("book_id") String bookId) {
        model.addAttribute("id", bookId);
        return "createComment";
    }

    @PostMapping("/book/{book_id}/comment")
    public String createComment(@PathVariable("book_id") String bookId, String comment) {
        try {
            commentService.addComment(comment, bookId);
            return "redirect:/book/" + bookId;
        } catch (BookNotFoundException e) {
            return "bookNotFound";
        }
    }

    @DeleteMapping("/book/{book_id}/comment/{id}")
    public String deleteComment(@PathVariable("book_id") String bookId, @PathVariable("id") String commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/book/" + bookId;
    }

}
