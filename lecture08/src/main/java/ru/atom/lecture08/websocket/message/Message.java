package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private final Topic topic;
    private final String login;
    private final Date date;
    private final String msg;

    public Message(Topic topic, String login, Date date, String msg) {
        this.topic = topic;
        this.login = login;
        this.date = date;
        this.msg = msg;
    }

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("login") JsonNode login, @JsonProperty("msg") JsonNode msg) {
        this.topic = topic;
        this.login = login.toString();
        this.date = new Date();
        this.msg = msg.toString();
    }

    public Topic getTopic() {
        return topic;
    }

    public String getLogin() {
        return login;
    }

    public Date getDate() {
        return date;
    }

    public String getMsg() {
        return msg;
    }
}
