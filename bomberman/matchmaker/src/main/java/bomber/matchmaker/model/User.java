package bomber.matchmaker.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
@Table(name = "user", schema = "bomber")
public class User {


    @ManyToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "game_id", nullable = false)
    private GameSession gameSession;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    public GameSession getGameSession() {
        return gameSession;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
        return this;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User: " + "id=" + id + ", name='" + name + "', gameId=" + gameSession.getGameId() + "\n";
    }
}
