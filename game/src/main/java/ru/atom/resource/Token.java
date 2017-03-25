package ru.atom.resource;

import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Created by zarina on 23.03.17.
 */
public class Token {
    private UUID token;
    private User user;

    public Token(User user) {
        this.user = user;

        this.token = UUID.randomUUID();
    }

    public UUID getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }


}
