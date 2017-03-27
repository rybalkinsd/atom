package ru.atom;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by pavel on 23.03.17.
 */
public class Tocken {

    private static AtomicLong counter = new AtomicLong(0);

    private Tocken() {
    }

    /**
     * Method for generating unique tocken's*
     *
     * @return unique tocken
     */
    public static Long generateTocken() {
        return counter.getAndIncrement();
    }
}
