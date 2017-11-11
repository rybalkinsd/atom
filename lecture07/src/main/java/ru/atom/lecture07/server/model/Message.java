package ru.atom.lecture07.server.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "message", schema = "chat")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne (cascade = PERSIST)
    private User user;

    @Column(name = "timestamp", nullable = false)
    private Timestamp time;

    @Column(name = "value", nullable = false)
    private String value;

    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Timestamp getTime() {
        return time;
    }

    public Message setTime(Timestamp timestamp) {
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

    private String getElapsedTime() {
        long timeDiff = System.currentTimeMillis() - this.time.getTime();
        if (timeDiff < 60000) return "recently";
        if (timeDiff < 3600000) return timeDiff / 60000 + " mins ago";
        if (timeDiff > 3600000) return "more then an hour ago";
        return "";
    }

    @Override
    public String toString() {
        return "<p style=\"color: #ff0000; display: inline;\">"
                + user.getLogin() + ":</p> <p style=\"display: inline;\">"
                + value + "</p>"
                + "<p style=\"color: #32CD32; display: inline;\"><em> "
                + getElapsedTime() + "</em></p>";
    }
}
