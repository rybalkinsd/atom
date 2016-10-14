package server.model.token;

import org.jetbrains.annotations.NotNull;
import server.model.user.User;

import java.util.concurrent.ConcurrentHashMap;

public class TokensContainer {

    private static ConcurrentHashMap<User, Token> tokensByUsersMap;
    private static ConcurrentHashMap<Token, User> usersByTokensMap;

    static {
        tokensByUsersMap = new ConcurrentHashMap<>();
        usersByTokensMap = new ConcurrentHashMap<>();
    }

    public static ConcurrentHashMap<User, Token> getTokensByUsersMap() {
        return tokensByUsersMap;
    }

    public static ConcurrentHashMap<Token, User> getUsersByTokensMap() {
        return usersByTokensMap;
    }

    public static void addToken(@NotNull User user, @NotNull Token token) {
        tokensByUsersMap.put(user, token);
    }

    public static void addUser(@NotNull Token token, @NotNull User user) {
        usersByTokensMap.put(token, user);
    }

    public static void removeToken(@NotNull Token token) {
        User user = usersByTokensMap.get(token);
        usersByTokensMap.remove(token);
        if (user != null) {
            tokensByUsersMap.remove(user);
        }
    }

}
