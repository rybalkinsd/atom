package ru.atom.resource;

import java.util.Map;
import java.util.UUID;

/**
 * Created by zarina on 25.03.17.
 */
public class TokenStorage extends AbstractStorage<UUID, Token> {
    public UUID getToken(User user){
        for (Map.Entry<UUID, Token> entry : memory.entrySet()) {
            if (entry.getValue().getUser().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }
}