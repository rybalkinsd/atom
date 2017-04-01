package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.security.SecureRandom;
import java.util.Collection;


class TokenStorage {
    private static final Logger log = LogManager.getLogger(TokenStorage.class);

    static final ConcurrentHashMap<Token, User> tokenStorage = new ConcurrentHashMap<>();
    private static long key;

    static void init() {
        key = Math.abs(new SecureRandom().nextLong());
        log.info("new key = " + key);
    }

    private TokenStorage() {}

    static boolean put(User user) {
        Token token = new Token(key, user.getName());

        // коллизия с одинаковыми токенами для разных пользователей
        if (tokenStorage.containsKey(token) && tokenStorage.get(token) != user) {
            log.warn(String.format("[%s] and [%s] have the same token %s",
                    user.getName(), tokenStorage.get(token).getName(), token.string()));
            return false;
        }

        user.setToken(token);
        tokenStorage.put(token, user);
        log.info(String.format("[%s] added", user.getName()));
        return true;
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

    static void remove(User user) {
        Token token = user.getToken();
        tokenStorage.remove(token);
        user.setToken(null);
    }

    static Collection<User> getAllLoginedUsers() {
        return tokenStorage.values();
    }

    static void clear() {
        tokenStorage.clear();
    }
}
