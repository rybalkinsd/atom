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
    private int id;

    @Column(name = "gameID", nullable = false)
    private long gameID;

    @Column(name = "user", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = RegistredEntity.class)
    private RegistredEntity user;

    @Column(name = "userpoints", nullable = false)
    private int userGamePoints;

    public UserGameResult(long gameID, RegistredEntity user, int userGamePoints) {
        this.gameID = gameID;
        this.user = user;
        this.userGamePoints = userGamePoints;
    }

    public long getGameID() {
        return gameID;
    }

    public RegistredEntity getUser() {
        return user;
    }

    public int getUserGamePoints() {
        return userGamePoints;
    }
}
