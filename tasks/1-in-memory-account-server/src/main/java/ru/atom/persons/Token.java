package ru.atom.persons;

import java.util.Random;

/**
 * Created by BBPax on 24.03.17.
 */
public class Token {
    private static Random randNumb = new Random();

    private long token;

    public long getToken() {
        return token;
    }

    public Token() {
        token = randNumb.nextLong();
    }

    public Token(String value) {
        token = Long.valueOf(value);
    }

    public void changeValue() {
        token = randNumb.nextLong();
    }

    @Override
    public String toString() {
        return Long.toString(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token temp = (Token) obj;
            return token == temp.getToken();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((Long)token).hashCode();
    }
}
