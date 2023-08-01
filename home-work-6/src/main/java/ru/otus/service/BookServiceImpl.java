package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book insert(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
