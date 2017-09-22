package ru.atom.lecture10.dataraces;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alpi
 * @since 21.11.16
 */
public class Stopper {
    public static final int HUNDRED_MILLION = 100_000_000;
    private AtomicInteger counter = new AtomicInteger();

    public boolean stop() {
        return counter.incrementAndGet() > HUNDRED_MILLION;
    }
}
