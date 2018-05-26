package ru.atom.lecture08.websocket.model;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "chat")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "color", nullable = false)
    private String color;

    public User setColor(String color) {
        this.color = color;
        return this;
    }

    public String getColor() {

        return color;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
