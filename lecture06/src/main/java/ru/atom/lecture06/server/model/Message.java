package ru.atom.lecture06.server.model;

import java.util.Date;

/**
 * Created by sergey on 3/25/17.
 */
public class Message {
    private User user;
    private Date timestamp;
    private String value;

    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Message setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Message setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user=" + user +
                ", timestamp=" + timestamp +
                ", value='" + value + '\'' +
                '}';
    }
}
