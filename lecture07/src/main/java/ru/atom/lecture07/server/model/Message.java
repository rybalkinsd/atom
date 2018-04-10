package ru.atom.lecture07.server.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message",schema = "chat")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @Column(name = "time",unique = false,nullable = false)
    private Date time = new Date();

    @Column(name = "value",unique = false,nullable=false,length = 140)
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
        return "[" + user.getLogin() + "]: " + getValue();
    }
}
