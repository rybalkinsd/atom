package ru.atom;

import static ru.atom.AuthServer.random;

public class Token {
    private String user;
    private long token;

    public Token(String user, long token) {
        this.user = user;
        this.token = token;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public String getUser() {

        return user;
    }

    public long getToken() {
        return token;
    }
}
