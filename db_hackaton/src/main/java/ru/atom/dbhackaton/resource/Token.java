package ru.atom.dbhackaton.resource;


import javax.persistence.*;

/**
 * Created by BBPax on 24.03.17.
 */
@Entity(name = "token")
@Table(schema = "game", name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "token", unique = true, nullable = false)
    private Long token;

//    @OneToOne(fetch = FetchType.EAGER, mappedBy = "token", cascade = CascadeType.PERSIST)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public Token setId(int id) {
        this.id = id;
        return this;
    }

    public Token setToken(Long token) {
        this.token = System.currentTimeMillis() * 100000L + (long) user.hashCode();
        return this;
    }

    public Token setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return getToken().toString();
    }


}
