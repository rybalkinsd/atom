package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Message {
    private final String topic;
    private final String data;

    public Message(String topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public Message(@JsonProperty("topic") String topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public String getTopic() {
        return topic;
    }

    public String getData() {
        return data;
    }
}
