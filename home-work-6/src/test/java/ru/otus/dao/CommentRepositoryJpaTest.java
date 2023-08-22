package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.NoFoundBookException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс CommentRepositoryJpaTest ")
@DataJpaTest
@Import({CommentRepositoryJpa.class, BookRepositoryJpa.class})
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращает ожидаемую комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book book = new Book(1,"Evgeniy Onegin", author, genre);
        Comment expectedComment = new Comment(1, "first comment", book);
        Optional<Comment> actualComment = commentRepository.getById(expectedComment.getId());
        assertEquals(Optional.of(expectedComment), actualComment);
    }

    @DisplayName("возвращает ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> comments = commentRepository.getAllCommentToBook(1);
        assertEquals(2, comments.size());
        List<Comment> comments2 = commentRepository.getAllCommentToBook(2);
        assertEquals(0, comments2.size());
    }

    @DisplayName("добавляет комментарий в БД")
    @Test
    void shouldInsertComment() {
        int beforeSize =  commentRepository.getAllCommentToBook(1).size();

        Book book = bookRepository.getById(1).get();
        Comment expectedComment = new Comment("first comment", book);

        Comment comment = commentRepository.insert(expectedComment);
        Optional<Comment> actualComment = commentRepository.getById(comment.getId());
        assertEquals(Optional.of(expectedComment), actualComment);

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет комментарий в БД")
    @Test
    void shouldUpdateComment() {
        int beforeSize =  commentRepository.getAllCommentToBook(1).size();

        Book book = bookRepository.getById(1).get();
        Comment expectedComment = new Comment(1, "new comment", book);

        commentRepository.update(expectedComment);
        Optional<Comment> actualComment = commentRepository.getById(expectedComment.getId());
        assertEquals(Optional.of(expectedComment), actualComment);

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет комментарий в БД по его id")
    @Test
    void shouldDeleteComment() throws NoFoundBookException {
        int beforeSize =  commentRepository.getAllCommentToBook(1).size();

        commentRepository.deleteById(1);

        assertThrows(NoSuchElementException.class, () -> commentRepository.getById(1).orElseThrow());

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
