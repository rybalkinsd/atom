package ru.atom.dbhackaton.server.model;

import javax.jws.soap.SOAPBinding;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ilnur on 12.04.17.
 */


@Entity
@Table(name = "registered_users", schema = "auth_server")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String token;


    public User(String name, String password) {
        this.setLogin(name);
        this.setPassword(password);
    }

    public User() {
        this.setPassword(null);
        this.setLogin(null);
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public void setToken() {
        this.token = new Token(this.login, this.password).toString();
    }

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

