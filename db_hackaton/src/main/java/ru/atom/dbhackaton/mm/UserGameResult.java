package ru.atom.dbhackaton.mm;

import ru.atom.dbhackaton.hibernate.RegistredEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by ilysk on 16.04.17.
 */
@Entity
@Table(name = "userresults", schema = "chat", catalog = "chatdb_atom1")
public class UserGameResult {

    public UserGameResult() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gameId", nullable = false)
    private Integer gameId;

    @Basic
    @Column(name = "userpoints", nullable = false)
    private Integer userGamePoints;

    public UserGameResult(Integer gameId, RegistredEntity user, int userGamePoints) {
        this.gameId = gameId;
        this.user = user;
        this.userGamePoints = userGamePoints;
    }

    @ManyToOne
    @JoinColumn(name = "login")
    private RegistredEntity user;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public int getUserGamePoints() {
        return userGamePoints;
    }

    public void setUserGamePoints(Integer userGamePoints) {
        this.userGamePoints = userGamePoints;
    }

    public RegistredEntity getUser() {
        return user;
    }

    public void setUser(RegistredEntity user) {
        this.user = user;
    }
}
