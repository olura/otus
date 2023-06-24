package ru.otus.exception;

public class DataLoadingException extends Exception {

    public DataLoadingException(String msg) {
        super(msg);
    }

    public DataLoadingException(String msg, Exception e) {
        super(msg, e);
    }
}
