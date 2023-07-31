package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private long id;

    private String title;

    private Author author;

    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, String authorName, String genreTitle) {
        this.title = title;
        this.author = new Author(authorName);
        this.genre = new Genre(genreTitle);
    }

    public Book(long id, String title, String authorName, String genreTitle) {
        this.id = id;
        this.title = title;
        this.author = new Author(authorName);
        this.genre = new Genre(genreTitle);
    }
}
