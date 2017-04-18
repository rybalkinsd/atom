package ru.atom.dbhackaton.mm;

import ru.atom.dbhackaton.hibernate.RegistredEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

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

    @Column(name = "gameID", nullable = false)
    private long gameID;

    @Column(name = "login", nullable = false)
    private Integer login;

    @Column(name = "userpoints", nullable = false)
    private Integer userGamePoints;

    public UserGameResult(long gameID, Integer login, int userGamePoints) {
        this.gameID = gameID;
        this.login = login;
        this.userGamePoints = userGamePoints;
    }

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = RegistredEntity.class)
    private RegistredEntity user;

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
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
