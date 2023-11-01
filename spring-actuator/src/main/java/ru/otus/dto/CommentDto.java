package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {

    private long id;

    private String text;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
    }
}
