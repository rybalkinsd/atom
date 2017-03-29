package ru.atom;

/**
 * Created by alex on 27.03.17.
 */
public class Token {
    private static Long counter = new Long(0);

    private Token() {
    }

    public static Long createToken() {
        return counter++;
    }
}
