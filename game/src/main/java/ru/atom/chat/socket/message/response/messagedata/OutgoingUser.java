package ru.atom.chat.socket.message.response.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atom.chat.user.Player;

public class OutgoingUser {
    private static Logger log = LoggerFactory.getLogger(OutgoingUser.class);
    private final String sender;


    @JsonCreator
    public OutgoingUser(
            @JsonProperty("sender") String sender)
            throws IllegalArgumentException {
        if (sender == null) {
            log.error("Sender must be not null");
            throw new IllegalArgumentException();
        }
        this.sender = sender;
    }

    public OutgoingUser(Player player) {
        this.sender = player.getLogin();
    }

    public String getSender() {
        return sender;
    }
}
