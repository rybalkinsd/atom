package model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sl on 19.10.2016.
 */
public interface TokensCollection {
    public User getUserByToken(@NotNull Token token);
    public void addUser(@NotNull User user) throws IllegalArgumentException;
    public boolean authenticate(User user);
    public Token issueToken(@NotNull User user);
    public List<User> getLoginUsers();
    public void validateToken(String rawToken, int flag, String param) throws Exception;
    public List<LeaderBoard> getNLeaders(int N);
}
