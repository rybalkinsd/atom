package ru.atom.chat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Message {

    public Message(LocalDateTime timestamp, String message, User user) {
        this.timestamp = timestamp;
        this.message = message;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private LocalDateTime timestamp;
    private String message;
    private User user;
}
