package ru.atom.dbhackaton.server.resource;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ru.atom.dbhackaton.server.model.Token;

/**
 * Created by Юля on 28.03.2017.
 */
public class TokenHolder {
    private static ConcurrentHashMap<Token, String> tokensToNames;
    private static ConcurrentHashMap<String, Token> namesToTokens;

    public TokenHolder() {
        this.tokensToNames = new ConcurrentHashMap<>();
        this.namesToTokens = new ConcurrentHashMap<>();
    }

    public boolean put(String k, Token v) {
        if (!namesToTokens.containsKey(k) && !namesToTokens.containsValue(v)) {
            namesToTokens.put(k, v);
            tokensToNames.put(v, k);
            return true;
        }
        return false;
    }

    public Token getToken(String k) {
        return namesToTokens.get(k);
    }

    public String remove(Token k) {
        String removeName = tokensToNames.remove(k);
        namesToTokens.remove(removeName);
        return removeName;
    }


    public static boolean validateToken(Token validatedToken) {
        try {
            return namesToTokens.get(tokensToNames.get(validatedToken)).equals(validatedToken);
        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<String> allUsersOnline() {
        return new ArrayList<String>(tokensToNames.values());
    }

}
