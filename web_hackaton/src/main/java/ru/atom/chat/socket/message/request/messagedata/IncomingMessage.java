package ru.atom.chat.socket.message.request.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncomingMessage {
    private static Logger log = LoggerFactory.getLogger(IncomingMessage.class);
    private final String sender;
    private final String password;
    private final String msg;


    @JsonCreator
    public IncomingMessage(
            @JsonProperty("sender") String sender,
            @JsonProperty("password") String password,
            @JsonProperty("msg") String msg)
            throws IllegalArgumentException {
        if (sender == null)
            log.error("Sender must be not null");
        if (password == null)
            log.error("Password must be not null");
        if (msg == null)
            log.error("Message must be not null");
        if (sender == null || password == null || msg == null)
            throw new IllegalArgumentException();
        this.sender = sender;
        this.password = password;
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public String getPassword() {
        return password;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "{sender:" + sender + "msg:" + msg + "}";
    }
}
