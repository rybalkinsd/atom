package ru.atom.object;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;


/**
 * Created by Fella on 29.03.2017.
 */
public class Token {
    private int idToken;
    private User user;
    private long valueToken;


    public Token() {
        Random rnd = new Random();
        this.valueToken = rnd.nextLong();
    }

    public int getIdToken() {
        return idToken;
    }

    public Token setIdToken(int idToken) {
        this.idToken = idToken;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Token setUser(User user) {
        this.user = user;
        return this;
    }

    public long getValueToken() {
        return valueToken;
    }

    public Token setValueToken(long valueToken) {
        this.valueToken = valueToken;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        if (getIdToken() != token.getIdToken()) return false;
        if (getValueToken() != token.getValueToken()) return false;
        return getUser() != null ? getUser().equals(token.getUser()) : token.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdToken();
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (int) (getValueToken() ^ (getValueToken() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return Long.toString(valueToken);

    }
}
