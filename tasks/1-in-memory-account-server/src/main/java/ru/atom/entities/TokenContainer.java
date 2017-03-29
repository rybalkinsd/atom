package ru.atom.entities;

import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ikozin on 28.03.17.
 */
public class TokenContainer {
    private static final ConcurrentHashMap<Token, User> users = new ConcurrentHashMap<>();

    public static void add(Token token, User user) {
        users.put(token, user);
    }

    public static boolean containsUser(String username) {
        return users.containsValue(new User(username, null));
    }

    public static boolean validate(Long value) {
        return (containsToken(value) && get(value).hashCode() == value);
    }

    public static boolean clean() {
        int counter = 0;
        for (Token token: users.keySet()) {
            remove(token.getValue());
            counter++;
        }
        return (counter > 0);
    }

    public static boolean containsToken(Long value) {
        return users.containsKey(new Token(value));
    }

    public static User get(Long value) {
        return users.get(new Token(value));
    }

    public static boolean remove(Long value) {
        return (users.remove(new Token(value)) != null);
    }

    public static LinkedList<String> getAllUsernames() {
        LinkedList<String> usernames = new LinkedList<>();
        for (Token token: users.keySet()) {
            usernames.add(users.get(token).getUsername());
        }
        return usernames;
    }
}
