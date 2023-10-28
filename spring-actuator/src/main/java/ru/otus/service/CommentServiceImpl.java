package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.exception.BookNotFoundException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentToBook(long id) throws BookNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("The book with id " + id + " was not found");
        }
        return commentRepository.findByBookId(id);
    }

    @Override
    @Transactional
    public Comment addComment(String text, long bookId) throws BookNotFoundException {
        Book book =  bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("The book with id " + bookId + " was not found"));
        return commentRepository.save(new Comment(text, book));
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }
}
