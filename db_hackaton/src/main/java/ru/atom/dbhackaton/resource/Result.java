package ru.atom.dbhackaton.resource;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

/**
 * Created by BBPax on 19.04.17.
 */
@Entity(name = "result")
@Table(schema = "game", name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score", nullable = false)
    private Integer score;

    public Integer getId() {
        return id;
    }

    public Result setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Result setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Result setUser(User user) {
        this.user = user;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Result setScore(Integer score) {
        this.score = score;
        return this;
    }
}
