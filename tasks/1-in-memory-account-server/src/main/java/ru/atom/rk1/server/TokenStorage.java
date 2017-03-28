package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.security.SecureRandom;
import java.util.Collection;


class TokenStorage {
    private static final Logger log = LogManager.getLogger(TokenStorage.class);

    private static final ConcurrentHashMap<Token, User> tokenStorage = new ConcurrentHashMap<>();
    private static final long key;

    static {
        long newKey = new SecureRandom().nextLong();
        key = newKey >= 0 ? newKey : newKey * (-1);
        log.info("new key = " + key);
    }

    private TokenStorage() {}

    static void put(User user) {
        Token token = new Token(key, user.getName());
        user.setToken(token);
        tokenStorage.put(token, user);
        log.info(String.format("[%s] added", user.getName()));
    }

    static boolean validate(String token) {
        if (tokenStorage.containsKey(new Token(token))) {
            log.info(token + " - valid token");
            return true;
        }

        log.info(token + " - invalid token");
        return false;
    }

    static User getUser(String token) {
        return tokenStorage.get(new Token(token));
    }

    static User remove(User user) {
        Token token = user.getToken();
        return tokenStorage.remove(token);
    }

    public static Collection<User> getAllLoginedUsers() {
        return tokenStorage.values();
    }
}
