package ru.atom.lecture08.websocket.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message",schema = "chat")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "topic")
    @Enumerated(EnumType.STRING)
    private Topic topic;

    @Column(name = "value",unique = false, nullable = false)
    private String data;

    @Column(name = "time")
    private final Date time = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Message setTopicDB(Topic topic) {
        this.topic = topic;
        return this;
    }

    public Message setData(String data) {
        this.data = data;
        return this;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Message setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

}
