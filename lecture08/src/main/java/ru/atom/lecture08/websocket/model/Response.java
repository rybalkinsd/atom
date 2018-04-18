package ru.atom.lecture08.websocket.model;

import java.util.HashMap;

public class Response {

    private Topic topic;

    private HashMap<String,String> data;

    public HashMap<String, String> getData() {
        return data;
    }

    public Topic getTopic() {
        return topic;
    }
}
