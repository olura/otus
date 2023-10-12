package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    private final Genre genre1 = new Genre("Romance");

    private final Genre genre2 = new Genre("Drama");

    private final Author author1 = new Author("Pushkin");

    private final Author author2 = new Author("Dostoevskiy");

    private final Book book = new Book("Evgeniy Onegin", author1, genre1);


    @ChangeSet(order = "001", id = "dropDb", author = "olura", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenre", author = "olura")
    public void insertGenre(GenreRepository repository) {
        repository.save(genre1);
        repository.save(genre2);
        repository.save(new Genre("Fairy Tale"));
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "olura")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(author1);
        repository.save(author2);
        repository.save(new Author("Turgenev"));
        repository.save(new Author("Gogol"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "olura")
    public void insertBooks(BookRepository repository) {
        repository.save(book);
        repository.save(new Book("Prestuplenie i nakazanie", author2, genre2));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "olura")
    public void insertBooks(CommentRepository repository) {
        repository.save(new Comment("first comment", book));
        repository.save(new Comment("second comment", book));
    }
}
