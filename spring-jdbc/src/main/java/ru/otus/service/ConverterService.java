package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;

import java.util.Formatter;
import java.util.List;

@Service
public class ConverterService {

    private final String ansiReset = "\u001B[0m";

    private final String ansiBold = "\u001B[1m";

    private final String ansiBlack = "\u001B[30m";

    private final String ansiUnderlined = "\u001B[4m";

    public String convertListBooksToString(List<Book> books) {


        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("|%s%s%-30s|%-20s|%-10s%s|\n",
                ansiUnderlined, ansiBold, "Title", "Author", "Genre", ansiReset);

        for (Book book: books) {
            formatter.format("%s|%-30s|%-20s|%-10s|%s\n",
                    ansiBlack, book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle(), ansiReset);
        }
        return sb.toString();
    }
}