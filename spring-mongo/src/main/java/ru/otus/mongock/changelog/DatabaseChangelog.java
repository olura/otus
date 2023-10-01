package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "olura", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertRomance", author = "olura")
    public void insertLermontov(GenreRepository repository) {
        repository.save(new Genre("Romance"));
    }

    @ChangeSet(order = "003", id = "insertPushkin", author = "olura")
    public void insertPushkin(AuthorRepository repository) {
        repository.save(new Author("Pushkin"));
    }
}
