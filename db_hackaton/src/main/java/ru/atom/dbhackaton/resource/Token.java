package ru.atom.dbhackaton.resource;


import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

/**
 * Created by BBPax on 24.03.17.
 */
@Entity(name = "token")
@Table(schema = "game", name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "token", length = 100, unique = true, nullable = false)
    private Long token;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;

    public int getId() {
        return id;
    }

    public Token setId(int id) {
        this.id = id;
        return this;
    }

    // TODO: 13.04.17  хрень какая то потому что хочу стандартный конструктор и чтобы делалось все чейнингом
    public Token setToken(Long token) {
        this.token = token;
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
