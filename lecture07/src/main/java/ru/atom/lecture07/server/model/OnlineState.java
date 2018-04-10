package ru.atom.lecture07.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "online", schema = "chat")
public class OnlineState {
    @Id
    private Integer id;

    @Column(name = "login",unique = false, nullable = false, length = 20)
    private String login;

    public Integer getId() {
        return id;
    }

    public OnlineState setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public OnlineState setLogin(String login) {
        this.login = login;
        return this;
    }
}
