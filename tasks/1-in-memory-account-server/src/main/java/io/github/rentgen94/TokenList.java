package io.github.rentgen94;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Western-Co on 26.03.2017.
 */
public class TokenList {
    private ConcurrentHashMap<Token, User> tokens;

    public TokenList() {
        this.tokens = new ConcurrentHashMap<>();
    }

    public User getUser(Token token) {
        if (tokens.containsKey(token)) {
            return tokens.get(token);
        }
        return null;
    }

    public Token getToken(User user) {
        for (Map.Entry<Token, User> userTokenEntry: tokens.entrySet()) {
            if (userTokenEntry.getValue().equals(user)) {
                return userTokenEntry.getKey();
            }
        }
        return null;
    }

    public boolean containsKey(Token token) {
        return tokens.containsKey(token);
    }

    public String getAllUserInJson() {
        String json = "{ \"users\": [";
        if (tokens.size() > 0) {
            for (Map.Entry<Token, User> userTokenEntry: tokens.entrySet()) {
                if (userTokenEntry.getKey() != null) {
                    json = json + userTokenEntry.getValue().getInJson() + ", ";
                }
            }
            json = json.substring(0, json.lastIndexOf(","));
        }
        json = json + "]}";
        return json;
    }

    public void put(Token token, User user) {
        tokens.put(token, user);
    }

    public void remove(Token token) {
        tokens.remove(token);
    }
}
