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

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("login") String login, @JsonProperty("msg") String msg) {
        this.topic = topic;
        this.login = login;
        this.date = new Date();
        this.msg = msg;
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

    public String format() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String out = "";
        switch (topic)
        {
            case MESSAGE:
                out = "<font color=\"grey\">" + dateFormat.format(date) + "</font>"
                    + " <b style=\" color:" + "grey" + ";\">"
                    + login + "</b>: " + msg;
                break;
            case LOGIN:
                out = "<font color=\"grey\">" + dateFormat.format(date) + "</font>"
                        + " <b style=\" color:" + "grey" + ";\">"
                        + "admin" + "</b>: " + login + " logged in";
                break;
            case LOGOUT:
                out = "<font color=\"grey\">" + dateFormat.format(date) + "</font>"
                        + " <b style=\" color:" + "grey" + ";\">"
                        + "admin" + "</b>: " + login + " logged out";
                break;
        }
        return out;
    }
}
