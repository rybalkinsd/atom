package ru.atom;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TokenList {

    private ConcurrentHashMap<String, Token> tokenList = new ConcurrentHashMap<>();

    public void addToken(Token token) {
        tokenList.put(token.getTokenName(), token);
    }

    public void deleteToken(Token token) {
        tokenList.remove(token.getTokenName());
    }

    public void deleteToken(String tokenName) {
        tokenList.remove(tokenName);
    }

    public boolean isValid(Token token) {
        return tokenList.containsKey(token.getTokenName());
    }

    public boolean isValid(String tokenName) {
        return tokenList.containsKey(tokenName);
    }

    public User getUserByToken(Token token) {
        Token t1 = tokenList.get(token.getTokenName());
        return t1 == null ? null : token.getUser();
    }

    public User getUserByToken(String tokenName) {
        Token token = tokenList.get(tokenName);
        return token == null ? null : token.getUser();
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> userList = new ArrayList<>();
        for (String s : tokenList.keySet()) {
            Token token = tokenList.get(s);
            userList.add(getUserByToken(token).getName());
        }
        return userList;
    }
}
