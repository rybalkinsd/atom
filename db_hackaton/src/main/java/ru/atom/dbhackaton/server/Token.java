package ru.atom.dbhackaton.server;

import java.security.SecureRandom;

/**
 * Created by gammaker on 29.03.2017.
 */
public class Token {
    public final long value;

    private static SecureRandom random = new SecureRandom();

    public Token(long value) {
        this.value = value;
    }

    public Token(String name, Long passwordHash) {
        long value = ((long)name.hashCode()) << 32;
        value |= passwordHash.hashCode();
        value ^= random.nextLong();
        this.value = value;
    }

    @Override public String toString() {
        return String.valueOf(value);
    }

    @Override public boolean equals(Object token) {
        if (this == token) return true;
        if (token == null || !(token instanceof Token)) return false;
        return value == ((Token) token).value;
    }
}
