package ru.atom.chat.socket.message.response.messagedata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.chat.socket.topics.ResponseTopic;

public class ResponseData {
    private final ResponseTopic topic;
    private final String data;


    public ResponseData(ResponseTopic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public ResponseData(@JsonProperty("topic") ResponseTopic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public String getData() {
        return data;
    }

    public ResponseTopic getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "{incomingTopic:" + topic + ",data:" + data + "}";
    }
}
