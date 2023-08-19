package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Genre {
    private long id;

    private String title;

    public Genre(String title) {
        this.title = title;
    }
}
