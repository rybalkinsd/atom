package gs.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Message {
    private final Topic topic;
    private final String data;
    private String owner;

    public Message(Topic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    public void setOwner(String name) {
        this.owner = name;
    }

    public String getOwner() {
        return owner;
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
}
