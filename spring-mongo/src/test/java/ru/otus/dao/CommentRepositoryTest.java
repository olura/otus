package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс CommentRepositoryJpaTest ")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращает ожидаемую комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        Author author = new Author("1","Pushkin");
        Genre genre = new Genre("1", "Romance");
        Book book = new Book("1","Evgeniy Onegin", author, genre);
        Comment expectedComment = new Comment("1", "first comment", book);
        Comment actualComment = commentRepository.findById(expectedComment.getId()).get();
        assertEquals(expectedComment.getId(), actualComment.getId());
    }

    @DisplayName("возвращает ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> comments = commentRepository.findByBookId("1");
        assertEquals(2, comments.size());
        List<Comment> comments2 = commentRepository.findByBookId("2");
        assertEquals(0, comments2.size());
    }

    @DisplayName("добавляет комментарий в БД")
    @Test
    void shouldInsertComment() {
        int beforeSize = commentRepository.findByBookId("1").size();

        Book book = bookRepository.findById("1").get();
        Comment expectedComment = new Comment("first comment", book);

        Comment comment = commentRepository.save(expectedComment);
        Comment actualComment = commentRepository.findById(comment.getId()).get();
        assertEquals(expectedComment, actualComment);

        int afterSize =  commentRepository.findByBookId("1").size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет комментарий в БД")
    @Test
    void shouldUpdateComment() {
        int beforeSize =  commentRepository.findByBookId("1").size();

        Book book = bookRepository.findById("1").get();
        Comment expectedComment = new Comment("1", "new comment", book);

        commentRepository.save(expectedComment);
        Comment actualComment = commentRepository.findById(expectedComment.getId()).get();
        assertEquals(expectedComment, actualComment);

        int afterSize =  commentRepository.findByBookId("1").size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет комментарий в БД по его id")
    @Test
    void shouldDeleteComment() {
        int beforeSize =  commentRepository.findByBookId("1").size();

        commentRepository.deleteById("2");

        assertEquals(Optional.empty(), commentRepository.findById("2"));

        int afterSize =  commentRepository.findByBookId("1").size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
