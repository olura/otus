package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.NoFoundBookException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс CommentRepositoryJpaTest ")
@DataJpaTest
@Import({CommentRepositoryJpa.class, BookRepositoryJpa.class})
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает ожидаемую комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        Author author = new Author(1,"Pushkin");
        Genre genre = new Genre(1, "Romance");
        Book book = new Book(1,"Evgeniy Onegin", author, genre);
        Comment expectedComment = new Comment(1, "first comment", book);
        Comment actualComment = commentRepository.getById(expectedComment.getId()).get();
        assertEquals(expectedComment.getId(), actualComment.getId());
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

        Book book = entityManager.find(Book.class, 1);
        Comment expectedComment = new Comment("first comment", book);

        Comment comment = commentRepository.save(expectedComment);
        Comment actualComment = entityManager.find(Comment.class, comment.getId());
        assertEquals(expectedComment, actualComment);

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("обновляет комментарий в БД")
    @Test
    void shouldUpdateComment() {
        int beforeSize =  commentRepository.getAllCommentToBook(1).size();

        Book book = entityManager.find(Book.class, 1);
        Comment expectedComment = new Comment(1, "new comment", book);

        commentRepository.save(expectedComment);
        Comment actualComment = entityManager.find(Comment.class, expectedComment.getId());
        assertEquals(expectedComment, actualComment);

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize, afterSize);
    }

    @DisplayName("удаляет комментарий в БД по его id")
    @Test
    void shouldDeleteComment() throws NoFoundBookException {
        int beforeSize =  commentRepository.getAllCommentToBook(1).size();

        commentRepository.deleteById(1);

        assertNull(entityManager.find(Comment.class, 1));

        int afterSize =  commentRepository.getAllCommentToBook(1).size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
