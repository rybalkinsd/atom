package ru.atom.authserver;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gammaker on 28.03.2017.
 */
public final class TokenStore {
    private static ConcurrentHashMap<Long, User> tokenUserMap = new ConcurrentHashMap<>();

    public static User getUserByToken(Token token) {
        return tokenUserMap.get(token.value);
    }

    public static Collection<User> getAllLoginedUsers() {
        return tokenUserMap.values();
    }

    public static boolean isTokenValid(Token token) {
        return tokenUserMap.containsKey(token.value);
    }

    public static void removeToken(Token token) {
        tokenUserMap.remove(token.value);
    }

    public static Token getTokenForUser(User user) {
        Token token = user.getToken();
        if (token != null) return token;
        while (token == null || tokenUserMap.contains(token)) {
            token = new Token(user.name, user.password);
        }
        user.setToken(token);
        tokenUserMap.put(token.value, user);
        return token;
    }

    private TokenStore() {}
}
