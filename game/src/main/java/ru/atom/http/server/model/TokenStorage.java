package ru.atom.http.server.model;

import java.util.Map;

/**
 * Created by zarina on 25.03.17.
 */
public class TokenStorage extends AbstractStorage<Long, Token> {
    public Long getToken(User user) {
        for (Map.Entry<Long, Token> entry : memory.entrySet()) {
            if (entry.getValue().getUser().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean validToken(String token) {
        Long tokenN = Long.parseLong(token);
        return memory.get(tokenN) != null;
    }
}
