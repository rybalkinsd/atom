package ru.atom.dbhackaton.server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pavel on 15.04.17.
 */
@Entity
@Table(name = "game_sessions", schema = "hackaton")
public class GameSession implements Serializable{

    public final static int PLAYERS_IN_GAME = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userScores", nullable = false)
    private String userScoresJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserScoresJson() {
        return userScoresJson;
    }

    public void setUserScoresJson(String userScoresJson) {
        this.userScoresJson = userScoresJson;
    }
}
