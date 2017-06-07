package ru.atom.dbhackaton.server.model;

import java.util.Random;

/**
 * Created by serega on 26.03.17.
 */
public class Token {
    private static Random random = new Random();

    public Token() {
    }

    public static Long createToken() {
        long counter = random.nextLong();
        return ((Long) counter);
    }
}
