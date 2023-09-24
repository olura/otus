//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ru.otus.dao;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Book;

public interface BookDao {
    Optional<Book> getById(long id);

    List<Book> getAll();

    Book insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
