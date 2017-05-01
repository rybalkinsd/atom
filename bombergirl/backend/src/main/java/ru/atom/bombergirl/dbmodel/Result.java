package ru.atom.bombergirl.dbmodel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;

/**
 * Created by ikozin on 17.04.17.
 */
@Entity
@Table(name = "result", schema = "game")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "game_id", unique = true, nullable = false)
    private Integer gameId;

    @ManyToOne()
    private User user;

    @Column(name = "score")
    private Integer score;

    public Result() {}

    public Result(Integer gameId, User user, Integer score) {
        this.gameId = gameId;
        this.user = user;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public User getUser() {
        return user;
    }

    public Integer getScore() {
        return score;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "[" + id + "," + gameId + "," + user.getName() + "," + score + "]";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Result)) return false;

        Result result = (Result) obj;
        if (result.getId().equals(this.id)
                && result.getScore().equals(this.score)
                && result.getUser().equals(this.user)
                && result.getGameId().equals(this.gameId)) return true;
        else return false;
    }
}
