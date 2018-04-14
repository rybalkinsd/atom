package ru.atom.lecture08.websocket.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "message", schema = "chat")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "time", nullable = false)
    private Date time = new Date();

    @Column(name = "value", nullable = false)
    private String value;

    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Message setTime(Date timestamp) {
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

    @Override
    public String toString() {
        return "SocketMessage{" +
                "user=" + user +
                ", timestamp=" + time +
                ", value='" + value + '\'' +
                '}';
    }

    public String format() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return "<font color=\"grey\">" + dateFormat.format(time) + "</font>"
                + " <b style=\" color:" + user.getColor() + ";\">"
                + user.getLogin() + "</b>: " + value;
    }
}
