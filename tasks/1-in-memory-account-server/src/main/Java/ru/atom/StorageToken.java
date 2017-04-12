package ru.atom;

import org.eclipse.jetty.server.Authentication;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Created by Fella on 29.03.2017.
 */
public class StorageToken {

    private static ConcurrentHashMap<Token, User> tokensT = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<User, Token> tokensU = new ConcurrentHashMap<>();




    public static void add(Token token, User user) {
        tokensT.put(token, user);
        tokensU.put(user, token);

    }

    public static boolean isContainsToken(Token token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return tokensT.containsKey(token);
    }

    public static User getUserSt(Token token) {
        return tokensT.get(token) ;
    }


    public static boolean isContainsUser(User user) {
        return tokensT.containsValue(user);
    }

    public static Token getTokenSt(User user) {
        return tokensU.get(user);
    }

    public static boolean remove(Token token) {
        User user = tokensT.get(token);
        return (tokensT.remove(token, user) && tokensU.remove(user, token));
    }


}
