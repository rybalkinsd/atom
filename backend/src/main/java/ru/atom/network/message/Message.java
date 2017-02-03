package ru.atom.network.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Created by sergey on 2/2/17.
 */
public class Message {
    private final Topic topic;
    @JsonRawValue
    private final String data;

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") String data) {
        this.topic = topic;
        this.data = data;
    }

    Topic getTopic() {
        return topic;
    }

    String getData() {
        return data;
    }
}
