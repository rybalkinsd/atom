package ru.atom.dbhackaton.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import java.io.Serializable;

/**
 * Created by pavel on 12.04.17.
 */
@Entity
@Table(name = "userTockens", schema = "hackaton")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private long token;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;


    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
