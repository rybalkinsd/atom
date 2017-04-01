package ru.atom;

import java.util.Random;
import ru.atom.AuthService;

public class Token {

    protected long token;
    private String username;
    private String tokenName;

    public Token(String name) {
        username = name;
        generateToken();
        tokenToString(token);
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getUserName() {
        return username;
    }

    public long getToken() {
        return token;
    }

    public void generateToken() {
        Random rand = new Random();
        token = rand.nextLong();
    }

    public User getUser() {
        return AuthService.getUser(username);
    }

    public void tokenToString(Long val) {
        tokenName = val.toString();
    }
}
