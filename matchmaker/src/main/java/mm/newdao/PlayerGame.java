package mm.newdao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Table(name = "player_game", schema = "bomberman")
public class PlayerGame implements Serializable {
    @Id
    @ManyToOne
    private Player player;

    @Id
    @ManyToOne
    private Game game;

    public PlayerGame(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public PlayerGame(){
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerGame that = (PlayerGame) o;

        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        return game != null ? game.equals(that.game) : that.game == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (game != null ? game.hashCode() : 0);
        return result;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
