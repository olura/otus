package ru.otus.exception;

public class AuthorNotFoundException extends Exception {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
