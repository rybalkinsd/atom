package ru.atom.dbhackaton.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by serega on 26.03.17.
 */
@Entity
@Table(name = "reguser", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "hashcode", unique = true)
    private Integer hashcode;

    //private String password;

    public User() {}

    public User setTime(Date time) {
        this.time = time;
        return this;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public User setPassword(String password) {
//        this.password = password;
//        return this;
//    }

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

    public Date getTimestamp() {
        return time;
    }

    public int getHashCode() {
        return hashcode;
    }


    public User setHashCode(String password) {
        int hashcode = getLogin() != null ? getLogin().hashCode() : 0;
        hashcode = 42 * hashcode + (password != null ? password.hashCode() : 0);
        this.hashcode = hashcode;
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
