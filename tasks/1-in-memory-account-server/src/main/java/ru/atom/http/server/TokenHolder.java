package ru.atom.http.server;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hpechka on 29.03.2017.
 */
public class TokenHolder {
    private ConcurrentHashMap<String, Token> loginToToken = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Token, String> tokenToLogin = new ConcurrentHashMap<>();

    public void put(Token token, String login) {
        loginToToken.put(login, token);
        tokenToLogin.put(token, login);
    }

    public Token getToken(String login) {
        return loginToToken.get(login);
    }

    public String getLogin(Token token) {
        return tokenToLogin.get(token);
    }

    public boolean isValid(Token token) {
        return !tokenToLogin.containsKey(token);
    }

    public boolean isValid(String login) {
        return !loginToToken.containsKey(login);
    }

    public String removeToken(Token token) {
        String s = tokenToLogin.remove(token);
        loginToToken.remove(s);
        return s;
    }

    public ArrayList<String> loginedUsers() {
        return new ArrayList<String>(tokenToLogin.values());
    }

}
