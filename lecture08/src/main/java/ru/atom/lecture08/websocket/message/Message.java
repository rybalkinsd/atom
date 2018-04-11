package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    private Topic topic = Topic.START;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "value",unique = false, nullable = false)
    private String data;

    @Column(name = "topic")
    private String topicDB;

    @Column(name = "time")
    private final Date time = new Date();

    public Message(Topic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.topicDB = topic.toString();
        this.data = data.toString();
    }

    Topic getTopic() {
        return topic;
    }

    String getData() {
        return data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTopicDB(String topicDB) {
        this.topicDB = topicDB;
    }

    public void setData(String data) {
        this.data = data;
    }
}
