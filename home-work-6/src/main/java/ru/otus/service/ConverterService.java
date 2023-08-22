package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Formatter;
import java.util.List;

public class ConverterService {

    public static String convertListBooksToString(BookService bookService, List<Book> books) {
        final String ansiReset = "\u001B[0m";
        final String ansiBold = "\u001B[1m";
        final String ansiBlack = "\u001B[30m";
        final String ansiUnderlined = "\u001B[4m";

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("|%s%s%-30s|%-20s|%-10s|%-50s%s|\n",
                ansiUnderlined, ansiBold, "Title", "Author", "Genre", "Comments", ansiReset);

        for (Book book: books) {
            formatter.format("%s|%-30s|%-20s|%-10s|%-50s|%s\n",
                    ansiBlack, book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle(),
                    convertListCommentToString(bookService.getAllCommentToBook(book.getId())), ansiReset);
        }
        return sb.toString();
    }

    public static String convertListBooksToString(List<Comment> comments, Book book) {
        final String ansiReset = "\u001B[0m";
        final String ansiBold = "\u001B[1m";
        final String ansiBlack = "\u001B[30m";
        final String ansiUnderlined = "\u001B[4m";

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("|%s%s%-30s|%-20s|%-10s|%-50s%s|\n",
                ansiUnderlined, ansiBold, "Title", "Author", "Genre", "Comments", ansiReset);

        formatter.format("%s|%-30s|%-20s|%-10s|%-50s|%s\n",
                ansiBlack, book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle(),
                convertListCommentToString(comments), ansiReset);
        return sb.toString();
    }


    private static String convertListCommentToString(List<Comment> comments) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Comment comment: comments) {
            stringBuilder.append(comment.getId());
            stringBuilder.append(") '");
            stringBuilder.append(comment.getText());
            stringBuilder.append("' ");
        }
        return stringBuilder.toString();
    }
}
