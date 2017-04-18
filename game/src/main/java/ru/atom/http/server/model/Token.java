package ru.atom.http.server.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "token", schema = "game")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt;

    @Column(name = "value", nullable = false)
    private String token;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Token setUser(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        return this;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return getToken();
    }


}
