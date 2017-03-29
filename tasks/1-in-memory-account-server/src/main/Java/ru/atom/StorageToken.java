package ru.atom;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Created by Fella on 29.03.2017.
 */
public class StorageToken {

    private static ConcurrentHashMap<Token, User> tokens = new ConcurrentHashMap<>();



    public static void add(Token token, User user) {
        tokens.put(token, user);

    }

    public static boolean isContainsToken(Token token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return tokens.containsKey(token);
    }

    public static User getUserSt(Token token) {
        return tokens.get(token) ;
    }


    public static boolean isContainsUser(User user) {
        return tokens.containsValue(user);
    }

    public static Token getTokenSt(User user) {
        for (Map.Entry<Token, User> entry : tokens.entrySet()) {
            if (user.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException();
    }

    public static boolean remove(Token token) {
        return tokens.remove(token, tokens.get(token));
    }


}
