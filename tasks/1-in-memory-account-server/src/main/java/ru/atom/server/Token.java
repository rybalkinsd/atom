package ru.atom.server;

/**
 * Created by Ксения on 25.03.2017.
 */

public class Token {
    private Long token;

    public Token(Long token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    public Long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Token other = (Token) o;
        if (!token.equals(other.getToken())) return false;
        return true;
    }
}
