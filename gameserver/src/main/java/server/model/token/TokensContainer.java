package server.model.token;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import server.auth.Authentication;
import server.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class TokensContainer {

    private static final Logger log = LogManager.getLogger(TokensContainer.class);
    private static ConcurrentHashMap<User, Token> tokensByUsersMap;
    private static ConcurrentHashMap<Token, User> usersByTokensMap;

    static {
        tokensByUsersMap = new ConcurrentHashMap<>();
        usersByTokensMap = new ConcurrentHashMap<>();
    }

    public static void addToken(@NotNull User user, @NotNull Token token) {
        tokensByUsersMap.put(user, token);
    }

    public static void addUser(@NotNull Token token, @NotNull User user) {
        usersByTokensMap.put(token, user);
    }

    public static User getUser(@NotNull Token token) {
        return usersByTokensMap.get(token);
    }

    private static Token getToken(@NotNull User user) {
        return tokensByUsersMap.get(user);
    }

    @NotNull
    public static List<User> getUserList() {
        return new ArrayList<>(tokensByUsersMap.keySet());
    }

    public static void removeToken(@NotNull Token token) {
        User user = usersByTokensMap.get(token);
        usersByTokensMap.remove(token);
        if (user != null) {
            tokensByUsersMap.remove(user);
        }
    }

    public static boolean containsToken(@NotNull Token token) {
        return usersByTokensMap.containsKey(token);
    }

    public static Token issueToken(@NotNull String name) {

        User user = Authentication.getRegisterUsers().parallelStream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(null);

        System.out.println("user " + user);
        Token token = getToken(user);
        if (token != null) {
            return token;
        }

        token = new Token(ThreadLocalRandom.current().nextLong());
        log.info("Generate new token {} for User with name {}", token, name);
        TokensContainer.addToken(user, token);
        TokensContainer.addUser(token, user);
        return token;

    }

    @NotNull
    public static Token parseToken(String rawToken) {
        Long longToken = Long.parseLong(rawToken.substring("Bearer".length()).trim());
        return new Token(longToken);
    }

    public static void validateToken(@NotNull String rawToken) throws Exception {
        Token token = parseToken(rawToken);
        if (!containsToken(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", getUser(token));
    }

}
