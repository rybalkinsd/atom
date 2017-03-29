package ru.atom.model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Юля on 28.03.2017.
 */
public class TokenMap {
    private static final Logger log = LogManager.getLogger(TokenMap.class);

    private static ConcurrentHashMap<User, Token> tokens;
    private static ConcurrentHashMap<Token, User> tokensReversed;

    public TokenMap() {
        this.tokens = new ConcurrentHashMap<>();
        this.tokensReversed = new ConcurrentHashMap<>();
    }

    public boolean putToken(User user, Token token) {
        if (!tokens.containsKey(user) && !tokensReversed.containsValue(token)) {
            tokens.put(user, token);
            tokensReversed.put(token, user);
            return true;
        }
        return false;
    }

    public Token getToken(User user) {
        return tokens.get(user);
    }

    public User getUser(Token token) {
        return tokensReversed.get(token);
    }

    public User removeToken(Token token) {
        User removedUser = tokensReversed.remove(token);
        tokens.remove(removedUser);
        return removedUser;
    }

    public Token issueToken(User user, TokenMap tokenMap) {
        Token token = tokenMap.getToken(user);
        if (token != null) {
            log.info("User " + user.getName() + " logged already");
            return token;
        }

        token = new Token(ThreadLocalRandom.current().nextLong());
        tokenMap.putToken(user, token);
        log.info("User " + user.getName() + " logged in");
        return token;
    }

    public static boolean validateToken(Token validatedToken) {
        if (!tokensReversed.containsKey(validatedToken)) {
            log.info("Unregistered user is trying to connect server with wrong token: " + validatedToken.getId());
            return false;
        }
        log.info("Request from user " + tokensReversed.get(validatedToken).getName() +  " with token: " + validatedToken.getId());
        return true;
    }

    public ArrayList<User> loginedUsers() {
        return new ArrayList<User>(tokensReversed.values());
    }

}