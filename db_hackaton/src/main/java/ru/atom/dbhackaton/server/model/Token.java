package ru.atom;

import java.util.Random;

/**
 * Created by serega on 26.03.17.
 */
public class Token {
    private static Random random = new Random();

    private Token() {
    }

    public static Long createToken() {
        long counter = random.nextLong();
        return ((Long) counter);
    }
}
