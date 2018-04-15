package ru.atom.lecture08.websocket.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "message",schema = "chat")
public class Message {

    public static DateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Message(){}

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

    public Date getTime() {
        return time;
    }

    public boolean isLaterThan(Date time) {
        return this.time.compareTo(time) > 0;
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
