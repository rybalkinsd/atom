package ru.atom.matchmaker.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Alexandr on 25.11.2017.
 */
@Entity
@Table(name = "game", schema = "matchmaker")
public class Game {
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private GameStatus status;


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "match",
            schema = "matchmaker",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    private Set<Player> players;

    public long getId() {
        return id;
    }

    public Game setId(long id) {
        this.id = id;
        return this;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Game setStatus(GameStatus status) {
        this.status = status;
        return this;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Game setPlayers(Set<Player> players) {
        this.players = players;
        return this;
    }
}
