package ru.atom.http.server.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

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
    private Long token;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Long getToken() {
        return token;
    }

    public Token setUser(User user) {
        this.user = user;
        this.token = System.currentTimeMillis() * 100000L + (long) user.hashCode();
        return this;
    }

    @Override
    public String toString() {
            return getToken().toString();
    }


}
