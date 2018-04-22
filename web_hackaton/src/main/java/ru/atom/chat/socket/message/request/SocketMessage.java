package ru.atom.chat.socket.message.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.chat.socket.topics.IncomingTopic;

public class SocketMessage {
    private final IncomingTopic topic;
    private final String data;


    public SocketMessage(IncomingTopic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public SocketMessage(@JsonProperty("topic") IncomingTopic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public String getData() {
        return data;
    }

    public IncomingTopic getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "{topic:" + topic + ",data:" + data + "}";
    }
}
