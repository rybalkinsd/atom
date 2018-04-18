package ru.atom.lecture08.websocket.model;


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


@Entity
@Table(name = "user",schema = "chat")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login",nullable = false,unique = true)
    private String login;

    @Column(name = "online")
    private short online;

    @Column(name = "password")
    private String password;


    public User setOnline(short online) {
        this.online = online;
        return this;
    }


    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public short getOnline() {
        return online;
    }

    public String getLogin() {
        return login;
    }
}
