package ru.atom.chat.socket.message.request.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUser {
    private static Logger log = LoggerFactory.getLogger(RegisterUser.class);
    private final String sender;


    @JsonCreator
    public RegisterUser(
            @JsonProperty("sender") String sender)
            throws IllegalArgumentException {
        if (sender == null) {
            log.error("Sender must be not null");
            throw new IllegalArgumentException();
        }
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "{sender:" + sender + "}";
    }
}
