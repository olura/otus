package ru.otus.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService{

    private final PrintStream output;

    private final Scanner input;

    public IOServiceImpl(PrintStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public int readInt() {
        return input.nextInt();
    }

    @Override
    public void println(String str) {
        output.println(str);
    }

    @Override
    public void printlnWithPrompt(String str, String prompt) {
        output.println(str);
        output.println(prompt);
    }

    @Override
    public void println() {
        output.println();
    }

    @Override
    public String readLine() {
        return input.nextLine();
    }

    @Override
    public String readLineWithPrompt(String prompt) {
        println(prompt);
        return input.nextLine();
    }
}
