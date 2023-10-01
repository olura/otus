package ru.otus.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    private String text;

//    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
