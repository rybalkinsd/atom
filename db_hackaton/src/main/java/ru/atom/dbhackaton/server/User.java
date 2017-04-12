package ru.atom.dbhackaton.server;

/**
 * Created by gammaker on 25.03.2017.
 */

public class User {
    public final String name; //unique identifier of user
    public final String password;
    private Token token;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        token = null;
    }



    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }


    public boolean isLogined() {
        return token != null;
    }

    public void resetToken() {
        token = null;
    }

    public String toJson() {
        return "{\"name\" : \"" + name + "\"}";
    }
}
