package ru.otus.service;

public interface IOService {
    void println(String str);

    void printlnWithPrompt(String str, String prompt);

    void println();

    public int readInt();

    public String readLine();

    public String readLineWithPrompt(String prompt);
}
