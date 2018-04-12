package ru.atom.lecture08.websocket.model;


import javax.persistence.*;


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
