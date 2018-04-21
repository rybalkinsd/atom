package ru.atom.chat.socket.message.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.chat.socket.topics.OutgoingTopic;

public class ResponseMessage {
    private final OutgoingTopic topic;
    private final String data;


    public ResponseMessage(OutgoingTopic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public ResponseMessage(@JsonProperty("topic") OutgoingTopic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public String getData() {
        return data;
    }

    public OutgoingTopic getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "{topic:" + topic + ",data:" + data + "}";
    }
}
