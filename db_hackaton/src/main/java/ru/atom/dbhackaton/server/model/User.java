package ru.atom.dbhackaton.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by serega on 26.03.17.
 */
@Entity
@Table(name = "reguser", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    public User() {}

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
                    ", login='" + login + '\'' +
                    '}';
    }
}
