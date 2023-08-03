package ru.otus.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

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
