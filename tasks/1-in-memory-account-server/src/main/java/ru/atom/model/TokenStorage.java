package ru.atom.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */

public class TokenStorage {
    private static ConcurrentHashMap<Long, Token> tokens = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Token> tokensReversed = new ConcurrentHashMap();

    public Token getTokenForUser(String user) {
        return tokensReversed.get(user);
    }

    public boolean validateToken(Token token) {
        return tokens.containsKey(token.getToken());
    }

    public boolean validateToken(Long token) {
        return tokens.containsKey(token);
    }

    public Token getToken(Long token) {
        return tokens.get(token);
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList();
        for (Token t : tokens.values()) {
            users.add((String) t.getUser().getName());
        }
        return users;
    }

    public void addToken(Token token) {
        tokens.put(token.getToken(), token);
        tokensReversed.put(token.getUser().getName(), token);
    }

    public void removeToken(Token token) {
        tokens.remove(token.getToken());
        tokensReversed.remove(token.getUser().getName());
    }

    public void removeToken(Long token) {
        tokensReversed.remove(tokens.get(token).getUser().getName());
        tokens.remove(token);
    }

}
