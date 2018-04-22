package ru.atom.chat.socket.message.response.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atom.chat.message.Message;

public class OutgoingChatMessage {
    private static Logger log = LoggerFactory.getLogger(OutgoingChatMessage.class);
    private final String sender;
    private final String time;
    private final String msg;


    @JsonCreator
    public OutgoingChatMessage(
            @JsonProperty("sender") String sender,
            @JsonProperty("time") String time,
            @JsonProperty("msg") String msg)
            throws IllegalArgumentException {
        if (sender == null)
            log.error("Sender must be not null");
        if (time == null)
            log.error("Password must be not null");
        if (msg == null)
            log.error("Message must be not null");
        if (sender == null || time == null || msg == null)
            throw new IllegalArgumentException();
        this.sender = sender;
        this.time = time;
        this.msg = msg;
    }

    public OutgoingChatMessage(Message message) throws IllegalArgumentException {
        this.sender = message.getUser().getLogin();
        this.time = message.getTime();
        this.msg = message.getValue();
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }
}
