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

    public void println(String str) {
        output.println(str);
    }

    public void println() {
        output.println();
    }

}
