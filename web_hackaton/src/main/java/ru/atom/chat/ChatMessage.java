package ru.atom.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    private String text;
    private User usr;
    private LocalDateTime time;

    public ChatMessage(String text, User usr) {
        this.text = text;
        this.usr = usr;
        this.time = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "[ " + usr.getName() + " ]" + text + " ( "
                + time.format(DateTimeFormatter.ofPattern("dd-LLLL-yyyy HH:mm:ss")).toString() + " )";
    }

    public User getUsr() {
        return usr;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
