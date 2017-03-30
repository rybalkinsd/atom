package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class User {
    private static final Logger log = LogManager.getLogger(User.class);

    private String name;
    private String password;
    private Token token;

    User(String name, String password) {
        this.name = name;
        this.password = password;
        this.token = null;
        log.info("created new user [" + name + ":" + password + "]");
    }

    String getName() {
        return name;
    }

    void setToken(Token token) {
        this.token = token;
    }

    Token getToken() {
        return token;
    }

    boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    String toJson() {
        return "{\"name\" : \"" + name + "\"}";
    }
}
