package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        User oldUser = tokenStoreReversed.get(token);
        String password = oldUser.getPassword();
        User newUser = new User(newName, password);
        tokenStore.remove(oldUser);
        tokenStore.put(newUser, token);
        tokenStoreReversed.replace(token, oldUser, newUser);
        return oldUser.getUserName();
    }

    public void validateToken(String rawToken) throws Exception {
        Token token = new Token(rawToken);
        if (!tokenStoreReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokenStoreReversed.get(token));
    }

    @NotNull
    public Token issueToken(User user) {
        Token token = tokenStore.get(user);
        if (token != null) {
            return token;
        }
        token = Token.generateToken();
        tokenStore.put(user, token);
        tokenStoreReversed.put(token, user);
        return token;
    }
}
