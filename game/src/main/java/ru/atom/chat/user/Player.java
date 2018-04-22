package ru.atom.chat.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "player", schema = "chat")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public Player() {
        rating = 0;
    }

    public Player(String userName) {
        this.login = userName;
        rating = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Player setLogin(String login) {
        this.login = login;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "name:" + login + " id:" + id;
    }
}
