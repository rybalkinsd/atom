package ru.atom;

import java.util.Random;

/**
 * Created by ilysk on 27.03.17.
 */
public class Token {
    private static final String CHARS =
            "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
    private static final Random random = new Random();
    private static final int length = 7;

    private final String value;

    public Token() {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        this.value = token.toString();
    }

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        return value != null ? value.equals(token.value) : token.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value;
    }
}
