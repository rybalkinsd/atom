package ru.atom;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vlad on 26.03.2017.
 */
public class TokensStorage {
    private static final ConcurrentHashMap<Long, User> tokens = new ConcurrentHashMap<>();

    public static void addToken(Token token) {
        tokens.put(token.getToken(), token.getUser());
    }

    public static void removeToken(Long token) {
        tokens.remove(token);
    }

    public static boolean validateToken(String token) {
        return tokens.containsKey(Long.parseLong(token));
    }

    public static boolean validateToken(Token token) {
        return tokens.containsValue(token.getUser());
    }

    public static String toJson() {
        if (tokens.isEmpty()) {
            return "{\"users\" : []}";
        }

        String json = "{\"users\" : [";
        Iterator<User> it = tokens.values().iterator();
        User tmp;
        while (it.hasNext()) {
            tmp = it.next();
            if (tmp != null)
                json = json + "{" + tmp.getName() + "}, ";
        }
        json = json.substring(0, json.length() - 2) + "]}";
        return json;
    }
}
