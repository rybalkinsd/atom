package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoryMessage {
    private String topic = "";

    @JsonCreator
    public HistoryMessage(@JsonProperty(value = "topic", required = true) String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
