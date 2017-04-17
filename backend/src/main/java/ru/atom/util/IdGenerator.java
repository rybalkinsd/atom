package ru.atom.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private final AtomicInteger current = new AtomicInteger(0);

    public int next() {
        return current.getAndIncrement();
    }
}