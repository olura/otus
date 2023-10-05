package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс BookRepositoryJpaTest ")
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращает ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author("1","Pushkin");
        Genre genre = new Genre("1", "Romance");
        Book expectedBook = new Book("1","Evgeniy Onegin", author, genre);
        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("возвращает ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);
        assertEquals(2, books.size());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        int beforeSize = bookRepository.findAll().size();

        Author author = authorRepository.findById("1").get();
        Genre genre = genreRepository.findById("2").get();
        Book expectedBook = new Book("Test book", author, genre);

        Book book = bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(book.getId()).get();
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookRepository.findAll().size();
        assertEquals(beforeSize + 1, afterSize);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("обновляет книгу в БД")
    @Test
    void shouldUpdateBook() {
        int beforeSize =   bookRepository.findAll().size();
        Author author = authorRepository.findById("2").get();
        Genre genre = genreRepository.findById("3").get();
        Book expectedBook = bookRepository.findById("1").get();
        expectedBook.setTitle("New title");
        expectedBook.setAuthor(author);
        expectedBook.setGenre(genre);

        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
        System.out.println(actualBook);
        assertEquals(expectedBook, actualBook);

        int afterSize =  bookRepository.findAll().size();
        assertEquals(beforeSize, afterSize);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("удаляет книгу в БД по её id")
    @Test
    void shouldDeleteBook() {
        int beforeSize = bookRepository.findAll().size();

        bookRepository.deleteById("1");
        assertEquals(Optional.empty(), bookRepository.findById("1"));

        int afterSize = bookRepository.findAll().size();
        assertEquals(beforeSize - 1, afterSize);
    }
}
