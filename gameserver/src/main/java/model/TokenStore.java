package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {

    private static TokenStore instance;

    private static final Logger log = LogManager.getLogger(TokenStore.class);
    private ConcurrentHashMap<User, Token> tokenStore;
    private ConcurrentHashMap<Token, User> tokenStoreReversed;

    private TokenStore() {
        this.tokenStore = new ConcurrentHashMap<>();
        this.tokenStoreReversed = new ConcurrentHashMap<>();
    }

    public static TokenStore getInstance() {
        if (instance == null) {
            instance = new TokenStore();
        }
        return instance;
    }

    @NotNull
    public User deleteToken(Token token) {
        User user = tokenStoreReversed.get(token);
        tokenStoreReversed.remove(token);
        tokenStore.remove(user);
        return user;
    }


    @NotNull
    public User getUserbyToken(Token token) {
        return tokenStoreReversed.get(token);
    }

    @NotNull
    public String changeName(Token token, String newName) {
        User user = tokenStoreReversed.get(token);
        return user.changeName(newName);
    }

    public void validateToken(String rawToken) throws Exception {
        Token token = new Token(rawToken);
        if (!tokenStoreReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokenStoreReversed.get(token));
    }

    @NotNull
    public Token issueToken(String name, String password) {
        User user = null;
        for (Enumeration<User> e = tokenStoreReversed.elements(); e.hasMoreElements();) {
            User temp = e.nextElement();
            if (temp.getUserName().equals(name) && temp.getPassword().equals(password)) {
                user = temp;
                break;
            }
        }
        Token token = null;
        if (user != null)
        token = tokenStore.get(user);
        if (token != null) {
            return token;
        }
        token = Token.generateToken();
        if (user == null) {
            user = new User(name,password);
        }
        tokenStore.put(user, token);
        tokenStoreReversed.put(token, user);
        return token;
    }
}
