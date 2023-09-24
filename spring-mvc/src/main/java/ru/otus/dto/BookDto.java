package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Book;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private long id;

    private String title;

    private String author;

    private String genre;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor().getName();
        this.genre = book.getGenre().getTitle();
    }
}
