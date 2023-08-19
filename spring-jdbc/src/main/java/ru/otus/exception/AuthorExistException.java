package ru.otus.exception;

public class AuthorExistException extends Exception {

    public AuthorExistException(String message) {
        super(message);
    }
}