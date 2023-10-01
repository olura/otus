//package ru.otus.dao;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import ru.otus.domain.Author;
//import ru.otus.domain.Book;
//import ru.otus.domain.Genre;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@DisplayName("Класс BookRepositoryJpaTest ")
//@DataJpaTest
//public class BookRepositoryJpaTest {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @DisplayName("возвращает ожидаемую книгу по её id")
//    @Test
//    void shouldReturnExpectedBookById() {
//        Author author = new Author(1,"Pushkin");
//        Genre genre = new Genre(1, "Romance");
//        Book expectedBook = new Book(1,"Evgeniy Onegin", author, genre);
//        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
//        assertEquals(expectedBook, actualBook);
//    }
//
//    @DisplayName("возвращает ожидаемый список книг")
//    @Test
//    void shouldReturnExpectedBookList() {
//        List<Book> books = bookRepository.findAll();
//        System.out.println(books);
//        assertEquals(2, books.size());
//    }
//
//    @DisplayName("добавляет книгу в БД")
//    @Test
//    void shouldInsertBook() {
//        int beforeSize = bookRepository.findAll().size();
//
//        Author author = entityManager.find(Author.class, 1);
//        Genre genre = entityManager.find(Genre.class, 2);
//        Book expectedBook = new Book("Test book", author, genre);
//
//        Book book = bookRepository.save(expectedBook);
//        Book actualBook = entityManager.find(Book.class, book.getId());
//        assertEquals(expectedBook, actualBook);
//
//        int afterSize =  bookRepository.findAll().size();
//        assertEquals(beforeSize + 1, afterSize);
//
//
//    }
//
//    @DisplayName("обновляет книгу в БД")
//    @Test
//    void shouldUpdateBook() {
//        int beforeSize =   bookRepository.findAll().size();
//        Author author = entityManager.find(Author.class, 2);
//        Genre genre = entityManager.find(Genre.class, 3);
//        Book expectedBook = entityManager.find(Book.class, 1);
//        expectedBook.setTitle("New title");
//        expectedBook.setAuthor(author);
//        expectedBook.setGenre(genre);
//
//        bookRepository.save(expectedBook);
//        Book actualBook = entityManager.find(Book.class, expectedBook.getId());
//        System.out.println(actualBook);
//        assertEquals(expectedBook, actualBook);
//
//        int afterSize =  bookRepository.findAll().size();
//        assertEquals(beforeSize, afterSize);
//    }
//
//    @DisplayName("удаляет книгу в БД по её id")
//    @Test
//    void shouldDeleteBook() {
//        int beforeSize = bookRepository.findAll().size();
//
//        bookRepository.deleteById(1L);
//        assertNull(entityManager.find(Book.class, 1));
//
//        int afterSize = bookRepository.findAll().size();
//        assertEquals(beforeSize - 1, afterSize);
//    }
//}
