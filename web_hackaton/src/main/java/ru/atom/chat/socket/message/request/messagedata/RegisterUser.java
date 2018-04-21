package ru.atom.chat.socket.message.request.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUser {
    private static Logger log = LoggerFactory.getLogger(RegisterUser.class);
    private final String sender;
    private final String password;
    private final String passCopy;


    @JsonCreator
    public RegisterUser(
            @JsonProperty("sender") String sender,
            @JsonProperty("password") String password,
            @JsonProperty("passCopy") String passCopy)
            throws IllegalArgumentException {
        if (sender == null)
            log.error("Sender must be not null");
        if (password == null)
            log.error("Password must be not null");
        if (passCopy == null)
            log.error("Message must be not null");
        if (sender == null || password == null || passCopy == null)
            throw new IllegalArgumentException();
        this.sender = sender;
        this.password = password;
        this.passCopy = passCopy;
    }

    public String getSender() {
        return sender;
    }

    public String getPassword() {
        return password;
    }

    public String getPassCopy() {
        return passCopy;
    }

    @Override
    public String toString() {
        return "{sender:" + sender + "}";
    }
}
