package ru.atom.dbhackaton.server.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by pavel on 15.04.17.
 */
@Entity
@Table(name = "game_sessions", schema = "hackaton")
public class GameSession {

    public final static int PLAYERS_IN_GAME = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> players;

    public Integer getId() {
        return id;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }
}
