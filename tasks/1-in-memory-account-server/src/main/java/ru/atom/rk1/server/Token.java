package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Token {
    private static final Logger log = LogManager.getLogger(Token.class);

    private Long token;

    Token(long key, String name) {
        int hash = name.hashCode();
        this.token = key ^ (((long) hash << 32) | hash);
        log.info(String.format("new token [%s] %d", name, token));
    }

    Token(String token) {
        this.token = Long.parseLong(token);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other instanceof Token) {
            Token otherToken = (Token) other;
            return this.token.equals(otherToken.token);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(token);
    }

    String string() {
        return token.toString();
    }
}
