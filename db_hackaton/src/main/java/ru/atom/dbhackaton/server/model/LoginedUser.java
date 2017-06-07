package ru.atom.dbhackaton.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import java.util.Date;

/**
 * Created by serega on 16.04.17.
 */
@Entity
@Table(name = "loguser", schema = "auth")
public class LoginedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User reguser;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "token", nullable = false)
    private Long token = Token.createToken();

    public User getUser() {
        return reguser;
    }

    public LoginedUser setUser(User user) {
        this.reguser = user;
        return this;
    }

    public Date setTime() {
        return time;
    }

    public LoginedUser setTime(Date timestamp) {
        this.time = timestamp;
        return this;
    }

    public LoginedUser setToken(Long token) {
        this.token = token;
        return this;
    }
    public Long getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Logined User{" +
                "user=" + reguser +
                ", timestamp=" + time +
                ", token='" + token + '\'' +
                '}';
    }
}