package ru.atom.http.server.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    @Column
    @CreationTimestamp
    private Date create_at;

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
