package ru.atom.dbhackaton.mm.model;

public class Connection {
    private final String token;

    public Connection(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "with token='" + token + '\'' +
                '}';
    }
}