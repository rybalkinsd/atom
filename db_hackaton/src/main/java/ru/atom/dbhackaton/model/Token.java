package ru.atom.dbhackaton.model;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vladfedorenko on 26.03.17.
 */

public class Token {
    private long token;
    private User user;

    public Token(User user) {
        this.token = ThreadLocalRandom.current().nextLong();
        this.user = user;
    }

    public Token(User user, long token) {
        this.token = token;
        this.user = user;
    }

    public long getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        try {
            Token tokenObject = (Token) o;
            return tokenObject.getToken() == this.token;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.token);
    }
}
