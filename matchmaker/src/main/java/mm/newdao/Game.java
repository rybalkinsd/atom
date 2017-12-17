package mm.newdao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game", schema = "bomberman")
public class Game {
    @Id
    @Column(name = "gameId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerGame> players = new ArrayList<>();

    @Column(name = "result", nullable = false)
    private Integer result;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<PlayerGame> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        PlayerGame playerGame = new PlayerGame(player, this);
        players.add(playerGame);
    }

    public void setPlayers(List<PlayerGame> players) {
        this.players = players;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (players != null ? !players.equals(game.players) : game.players != null) return false;
        return result != null ? result.equals(game.result) : game.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = players != null ? players.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}
