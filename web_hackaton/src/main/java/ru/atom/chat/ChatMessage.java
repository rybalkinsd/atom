package ru.atom.chat;

import java.io.IOException;
import java.nio.file.*;
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

    void saveInFile(){
        String path = "./src/main/resources/history.txt";
        String text = getTime().toString() + " " + getUsr().getName() +
                ": " + getText() + "\n";
        try {
            Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
