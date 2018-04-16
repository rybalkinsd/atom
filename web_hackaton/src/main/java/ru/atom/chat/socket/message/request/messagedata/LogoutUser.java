package ru.atom.chat.socket.message.request.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutUser {
    private static Logger log = LoggerFactory.getLogger(LogoutUser.class);
    private final String sender;
    private final String password;


    @JsonCreator
    public LogoutUser(
            @JsonProperty("sender") String sender,
            @JsonProperty("password") String password)
            throws IllegalArgumentException {
        if (sender == null)
            log.error("Sender must be not null");
        if (password == null)
            log.error("Password must be not null");
        if (sender == null || password == null)
            throw new IllegalArgumentException();
        this.sender = sender;
        this.password = password;
    }

    public String getSender() {
        return sender;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "{sender:" + sender + "}";
    }
}
