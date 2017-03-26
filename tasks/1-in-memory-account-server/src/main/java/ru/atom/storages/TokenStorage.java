package ru.atom.storages;


import ru.atom.base.Token;
import ru.atom.base.User;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by mkai on 3/26/17.
 */
public class TokenStorage {
    private ConcurrentHashMap<Token, User> tokens;

    public TokenStorage() {
        tokens = new ConcurrentHashMap<>();
    }

    public boolean addToken(Token token, User user) {
        if (checkToken(token)) {
            return false;
        }
        tokens.put(token, user);
        return true;
    }

    public User getUser(Token token) {
        if (checkToken(token)) {
            return tokens.get(token);
        }
        return null;
    }

    public boolean checkToken(Token token) {
        return tokens.containsKey(token);
    }

    public Token generateToken() {
        final SecureRandom random = new SecureRandom();
        final long newValueToken = random.nextLong();
        final Token newToken = new Token(newValueToken);
        if (checkToken(newToken)) {
            generateToken();
        }

        return newToken;
    }
}
