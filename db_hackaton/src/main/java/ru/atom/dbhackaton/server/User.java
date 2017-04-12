package ru.atom.dbhackaton.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created by gammaker on 25.03.2017.
 */

@Entity
@Table(name = "user", schema = "chat")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    public final String name; //unique identifier of user

    @Column(name = "password", nullable = false, length = 20)
    public final String password;

    private Token token;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        token = null;
    }



    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
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
