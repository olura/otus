package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс CommentServiceImpl ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("возвращает список всех комментариев книги")
    @Test
    void shouldReturnExpectedCommentListToBook() {

        Author author1 = new Author(1,"Pushkin");
        Genre genre1 = new Genre(1, "Romance");
        Author author2 = new Author(2,"Test_author");
        Genre genre2 = new Genre(2, "Test_genre");
        Book book1 = new Book(1,"Evgeniy Onegin", author1, genre1);
        Book book2 = new Book(2,"Test book", author2, genre2);

        List<Comment> expectedComment = new ArrayList<>();
        expectedComment.add(new Comment("first comment", book1));
        expectedComment.add(new Comment("second comment", book2));

        given(commentRepository.findByBookId(anyLong())).willReturn(expectedComment);

        List<Comment> actualComment = commentService.getAllCommentToBook(1);
        assertEquals(expectedComment, actualComment);
    }


    @DisplayName("добавляет комментарий к книге")
    @Test
    void shouldInsertCommentToBookById() throws BookNotFoundException {
        Author author = new Author(1,"Test_author");
        Genre genre = new Genre(1, "Test_genre");
        Book book = new Book(1,"Test book", author, genre);
        Comment expectedComment = new Comment("first comment", book);

        given(commentRepository.save(any())).willReturn(expectedComment);
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));

        Comment actualComment = commentService.addComment(expectedComment.getText(), expectedComment.getBook().getId());
        verify(commentRepository, times(1)).save(any());
        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("удаляет комментарий в БД по его id")
    @Test
    void shouldDeleteComment() {

        long id = 1;

        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(commentRepository).deleteById(valueCapture.capture());

        commentRepository.deleteById(id);
        assertEquals(id, valueCapture.getValue());
    }
}
