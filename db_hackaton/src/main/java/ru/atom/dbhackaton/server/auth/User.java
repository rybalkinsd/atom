package ru.atom.dbhackaton.server.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by gammaker on 25.03.2017.
 */

@Entity
@Table(name = "user", schema = "bomber")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String name; //unique identifier of user

    @Column(name = "passwordHash", nullable = false)
    private Long passwordHash;

    @Column(name = "registrationDate", nullable = false)
    private Date registrationDate = new Date();

    @Column(name = "token")
    private Long token;

    public String name() {
        return name;
    }

    public Long passwordHash() {
        return passwordHash;
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        long hash = password.substring(password.length() / 2, password.length()).hashCode();
        hash |= (long) password.substring(password.length() / 2).hashCode() << 32;
        this.passwordHash = hash;
        token = null;
    }

    public int getId() {
        return id;
    }

    public boolean validatePassword(String password) {
        long hash = password.substring(password.length() / 2, password.length()).hashCode();
        hash |= (long) password.substring(password.length() / 2).hashCode() << 32;
        return hash == this.passwordHash.longValue();
    }


    public Token getToken() {
        return new Token(token);
    }

    public void setToken(Token token) {
        this.token = token.value;
    }


    public boolean isLogined() {
        return token != null;
    }

    public void resetToken() {
        token = null;
    }

    public String toJson() {
        return "{\"name\" : \"" + name + "\"}";
    }
}
