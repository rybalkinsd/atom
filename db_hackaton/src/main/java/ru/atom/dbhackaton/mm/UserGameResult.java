package ru.atom.dbhackaton.mm;

import ru.atom.dbhackaton.hibernate.RegistredEntity;

import javax.persistence.*;

/**
 * Created by ilysk on 16.04.17.
 */
@Entity
@Table(name = "userresults", schema = "chat", catalog = "chatdb_atom1")
public class UserGameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    @Column(name = "gameID", nullable = false)
    private long gameID;

    @Column(name = "login", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = RegistredEntity.class)
    private RegistredEntity login;

    @Column(name = "userpoints", nullable = false)
    private Integer userGamePoints;

    public UserGameResult(long gameID, RegistredEntity login, int userGamePoints) {
        this.gameID = gameID;
        this.login = login;
        this.userGamePoints = userGamePoints;
    }

    public long getGameID() {
        return gameID;
    }

    public RegistredEntity getLogin() {
        return login;
    }

    public int getUserGamePoints() {
        return userGamePoints;
    }
}
