package ru.atom.dbhackaton.server.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;


@Entity
@Table(schema = "hackaton", name = "game_results")
public class GameResults {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @Column(name = "points")
    private Integer points;

    public GameResults() {
    }

    public GameResults(Long gameId, User user, Integer points) {
        this.gameId = gameId;
        this.user = user;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public GameResults setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public GameResults setUser(User user) {
        this.user = user;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public GameResults setPoints(Integer points) {
        this.points = points;
        return this;
    }
}
