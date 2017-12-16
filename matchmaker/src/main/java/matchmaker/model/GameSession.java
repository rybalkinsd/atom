package matchmaker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gamesession", schema = "matchmaker")
public class GameSession {
    @Id
    private Long id;

    @Column(name = "playerCount", nullable = false)
    private Integer playerCount;

    public GameSession setId(Long id) {
        this.id = id;
        return this;
    }

    public GameSession setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '\'' + '}';
    }
}
