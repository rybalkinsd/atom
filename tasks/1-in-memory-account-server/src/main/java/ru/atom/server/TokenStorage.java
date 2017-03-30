package ru.atom.server;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Ксения on 25.03.2017.
 */
public class TokenStorage {
    private static ConcurrentHashMap<Token, String> map = new ConcurrentHashMap();
    public static final Random rand = new Random();

    public static void put(Token token, String name) {
        map.put(token, name);
    }

    public static String get(Token token) {
        return map.get(token);
    }

    public static boolean contains(Token token) {
        return map.containsKey(token);
    }

    public static Long generateUniqueToken() {
        Long token;
        do {
            token = TokenStorage.rand.nextLong();
        } while (contains(new Token(token)));
        return token;
    }

    public static String remove(Token token) {
        return map.remove(token);
    }


}
