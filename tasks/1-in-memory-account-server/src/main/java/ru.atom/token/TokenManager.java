package ru.atom.token;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import ru.atom.client.User;

/**
 * Created by salvador on 01.04.17.
 */
public class TokenManager {
    private ConcurrentHashMap<Token, User> tokens;

    public TokenManager() {
        this.tokens = new ConcurrentHashMap<>();
    }

    public void put(Token token, User user) {
        tokens.put(token, user);
    }

    public boolean containsKey(Token token) {
        return tokens.containsKey(token);
    }

    public void remove(Token token) {
        tokens.remove(token);
    }

    public ArrayList<String> returnAllUsers() {
                ArrayList<String> names = new ArrayList<>();
                for (User user : tokens.values()) {
                        names.add(user.getName());
                    }
                return names;
            }
}
