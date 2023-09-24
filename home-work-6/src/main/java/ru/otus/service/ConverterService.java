package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Formatter;
import java.util.List;

@Service
public class ConverterService {

    private static final String ANSI_RESET = "\u001B[0m";

    private static final String ANSI_BOLD = "\u001B[1m";

    private static final String ANSI_BLACK = "\u001B[30m";

    private static final String ANSI_UNDER_LINED = "\u001B[4m";

    public String convertListBooksToString(List<Book> books) {


        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("|%s%s%-30s|%-20s|%-10s%s|\n",
                ANSI_UNDER_LINED, ANSI_BOLD, "Title", "Author", "Genre", ANSI_RESET);

        for (Book book: books) {
            formatter.format("%s|%-30s|%-20s|%-10s|%s\n",
                    ANSI_BLACK, book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle(), ANSI_RESET);
        }
        return sb.toString();
    }

    public String convertListCommentToString(List<Comment> comments) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Comment comment: comments) {
            stringBuilder.append(ANSI_BLACK);
            stringBuilder.append(comment.getId());
            stringBuilder.append(") '");
            stringBuilder.append(comment.getText());
            stringBuilder.append("' ");
            stringBuilder.append(ANSI_RESET);
        }
        return stringBuilder.toString();
    }
}
