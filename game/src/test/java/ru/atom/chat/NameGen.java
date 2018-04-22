package ru.atom.chat;

import org.springframework.stereotype.Component;

public class NameGen {
    private static int testNumber = 0;

    public int getGenId() {
        return testNumber++;
    }

    public String generateName() {
        return "test" + (testNumber++);
    }
}
