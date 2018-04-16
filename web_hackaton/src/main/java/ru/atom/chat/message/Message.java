package ru.atom.chat.message;

import ru.atom.chat.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "message", schema = "chat")
public class Message implements IMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    //@ManyToOne(cascade = CascadeType.PERSIST)
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "time", nullable = false, length = 20)
    private String time;

    @Column(name = "value", nullable = false, length = 140)
    private String value;

    public Message() {
    }

    public Message(User user, String body) {
        this.user = user;
        this.value = body;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        this.time = format.format(new Date());
    }

    @Override
    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public String getTime() {
        return time;
    }

    public Message setTime(String timestamp) {
        this.time = timestamp;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Message setValue(String value) {
        this.value = value;
        return this;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
