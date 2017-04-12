package ru.atom.dbhackaton.server.storages;


import ru.atom.dbhackaton.server.base.Token;
import ru.atom.dbhackaton.server.base.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class TokenStorage {
    private static ConcurrentHashMap<Token, User> tokensMap = new ConcurrentHashMap<>();

    public static boolean addToken(Token token, User user) {
        if (checkToken(token)) {
            return false;
        }
        tokensMap.put(token, user);
        return true;
    }

    public static User getUser(Token token) {
        if (checkToken(token)) {
            return tokensMap.get(token);
        }
        return null;
    }

    public static User getUser(String tokenValue) {
        for (Token token : tokensMap.keySet()) {
            if (token.equals(tokenValue)) {
                return tokensMap.get(token);
            }
        }

        return null;
    }

    public static boolean checkToken(Token token) {
        return tokensMap.containsKey(token);
    }

//    public static Token generateToken() {
//        final SecureRandom random = new SecureRandom();
//        final long newValueToken = random.nextLong();
//        final Token newToken = new Token();
//        if (checkToken(newToken)) {
//            generateToken();
//        }
//
//        return newToken;
//    }

    public static boolean containsUser(User user) {
        return tokensMap.containsValue(user);
    }

    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> names = new ArrayList<>();
        for (User user : tokensMap.values()) {
            names.add(user.getName());
        }
        return names;
    }

    public static Token getToken(String valueToken) {
        for (Token token : tokensMap.keySet()) {
            if (token.equals(valueToken)) {
                return token;
            }
        }
        return null;
    }
}
