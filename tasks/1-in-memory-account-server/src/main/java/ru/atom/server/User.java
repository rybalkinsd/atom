package ru.atom.server;

/**
 * Created by Ксения on 24.03.2017.
 */
public class User {

    private Integer id;
    private String name;
    private String password;
    private Long token = null;

    private static int currId = -1;

    public User(String name, String password) {
        this.id = ++currId;
        this.name = name;
        this.password = password;
        this.token = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Long getToken() {
        return token;
    }

    public User setToken(Long token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "\"id\":\"" + getId() + "\", \"name\":\"" + getName() + "\", \"token\":\"" + getToken() + "\"";
    }
}
