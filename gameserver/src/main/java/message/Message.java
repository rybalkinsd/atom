package message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import gameobjects.Movable;

public class Message {
    private final Topic topic;
    private final String data;

    public Message(Topic topic, String data) {
        this.topic = topic;
        this.data = data;

    }

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public Topic getTopic() {
        return topic;
    }

    public String getData() {
        return data;
    }

    /*public Movable.Direction getDirection() {
        if (data=="UP")
    }*/
}