package ru.atom.lecture06.server.model;

/**
 * Created by sergey on 3/25/17.
 */
public class User {
    private long id;
    private String login;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }
}
