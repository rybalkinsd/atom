package ru.atom.lecture06.server.model;

import java.util.Date;

/**
 * Created by sergey on 3/25/17.
 */
public class Message {
    private long user;
    private long userId;
    private Date timestamp;
    private String value;

    public long getUser() {
        return user;
    }

    public Message setUser(long id) {
        this.user = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Message setUserId(long userId) {
        this.userId = userId;
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
}
