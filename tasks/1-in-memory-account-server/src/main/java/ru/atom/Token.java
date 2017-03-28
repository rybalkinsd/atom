package ru.atom;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by serega on 26.03.17.
 */
public class Token {
    private static AtomicLong counter = new AtomicLong(0);

    private Token() {
    }

    public static Long createToken() {
        return counter.getAndIncrement();
    }
}
