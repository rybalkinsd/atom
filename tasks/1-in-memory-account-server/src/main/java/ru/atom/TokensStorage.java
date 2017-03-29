package ru.atom;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.util.Iterator;

/**
 * Created by Vlad on 26.03.2017.
 */
public class TokensStorage {
    private static final ConcurrentArrayQueue<Token> tokens = new ConcurrentArrayQueue<>();

    public static void addToken(Token token) {
        tokens.add(token);
    }

    public static void removeToken(Long token) {
        tokens.remove(findByLong(token));
    }

    public static boolean validateToken(String token) {
        return tokens.contains(findByLong(Long.parseLong(token)));
    }

    public static boolean validateToken(Token token) {
        return tokens.contains(token);
    }

    private static Token findByLong(Long token) {
        Iterator<Token> it = tokens.iterator();
        Token tmp;
        while (it.hasNext()) {
            tmp = it.next();
            if (tmp != null && token == tmp.getToken())
                return tmp;
        }
        return null;
    }

    public static String toJson(){
        if (tokens.isEmpty()) {
            return "{\"users\" : []}";
        }
        String json = "{\"users\" : [";
        Iterator<Token> it = tokens.iterator();
        Token tmp;
        while (it.hasNext()) {
            tmp = it.next();
            if (tmp != null)
                json = json + "{" + tmp.getUser().toString() + "}, ";
        }
        json = json.substring(0, json.length() - 2) + "]}";
        return json;
    }
}
