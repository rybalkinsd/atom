package ru.atom.base;


import ru.atom.base.Token;

/**
 * Created by mkai on 3/26/17.
 */
public class User {
    public enum Status {
        ONLINE,
        OFFLINE
    }

    private String name;
    private String password;
    private Status status;
    private Token token;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        status = Status.OFFLINE;
        token = null;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


}
