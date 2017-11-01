package ru.atom.chat;

public class Message {
    private String time = "";
    private String senderName = "";
    private String data = "";

    public Message(String time, String senderName, String data) {
        this.data = data;
        this.senderName = senderName;
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public String getData() {
        return this.data;
    }

    public String getHtml() {
        return "<p><font color=\"red\">" +
                this.time +
                "</font>" + " " + "<font color=\"green\">" +
                this.senderName + "</font>" + ": " + this.data + "</p>";
    }
}
