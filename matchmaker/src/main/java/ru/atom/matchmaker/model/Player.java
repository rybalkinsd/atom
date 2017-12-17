package ru.atom.matchmaker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Alexandr on 25.11.2017.
 */
@Entity
@Table(name = "player", schema = "matchmaker")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true, nullable = false, updatable = false, length = 30)
    private String login;

    @Column(name = "password", nullable = false, updatable = false, length = 20)
    private String password;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private PlayerStatus status;

    @Column(name = "wins", nullable = false)
    private int wins;

    public int getId() {
        return id;
    }

    public Player setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Player setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public Player setStatus(PlayerStatus status) {
        this.status = status;
        return this;
    }

    public int getWins() {
        return wins;
    }

    public Player setWins(int wins) {
        this.wins = wins;
        return this;
    }

}
