package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    private String title;

//    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

//    @ManyToOne (targetEntity = Genre.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    public Book(String title, Author authorName, Genre genreTitle) {
        this.title = title;
        this.author = authorName;
        this.genre = genreTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
