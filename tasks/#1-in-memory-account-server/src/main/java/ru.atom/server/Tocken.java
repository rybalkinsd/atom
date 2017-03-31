package ru.atom.server;

/**
 * Created by konstantin on 25.03.17.
 */
public class Tocken {
    private static long counter = 0;

    private Tocken() {
    }

    /**
     * Method for generating unique tocken's*
     *
     * @return unique tocken
     */
    public static Long generateTocken() {
        return counter++;
    }
}
