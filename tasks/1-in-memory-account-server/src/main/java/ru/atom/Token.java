package ru.atom;

import java.util.Random;

/**
 * Created by alex on 27.03.17.
 */
public class Token {

    private static Random random = new Random();



    private Token() {
    }

    public static long createToken() {

        long counter = random.nextLong();
        return (Long) counter;
    }
}
