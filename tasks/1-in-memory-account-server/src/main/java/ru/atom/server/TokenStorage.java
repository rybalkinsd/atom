package ru.atom.server;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.util.Random;


/**
 * Created by Ксения on 25.03.2017.
 */
public class TokenStorage {
    private static ConcurrentArrayQueue<Token> map = new ConcurrentArrayQueue<>();
    public static final Random rand = new Random();

    public static void offer(Integer id, Long token) {
        map.offer(new Token(id, token));
    }

    public static Token getToken(Integer id) {
        for (Token token:map) {
            if (token.getId().equals(id)) return token;
        }
        return null;
    }

    public static boolean containsToken(Long currToken) {
        if (currToken == null) return false;
        for (Token token:map) {
            if (!(token.getToken() == null)) {
                if (token.getToken().equals(currToken)) return true;
            }
        }
        return false;
    }

    public static Long generateUniqueToken() {
        Long token;
        do {
            token = TokenStorage.rand.nextLong();
        } while (containsToken(token));
        return token;
    }

    public static Integer getTokenId(Long currToken) {
        if (currToken == null) return null;
        for (Token token:map) {
            if (token.getToken().equals(currToken)) return token.getId();
        }
        return  null;
    }

}
