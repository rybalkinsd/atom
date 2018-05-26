package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketMessage {
    private Topic topic;
    private final String login;
    private final Date date;
    private String msg;

    @JsonCreator
    public SocketMessage(@JsonProperty("topic") Topic topic, @JsonProperty("login") String login, @JsonProperty("msg") String msg) {
        this.topic = topic;
        this.login = login;
        this.date = new Date();
        this.msg = msg;
    }

    public SocketMessage setMsg(String msg) {
        this.msg = msg;
        return this;
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

    public SocketMessage setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }
}
