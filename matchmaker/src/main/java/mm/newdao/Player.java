package mm.newdao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import java.util.List;

@Entity
@Table(name = "player", schema = "bomberman")
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playerId;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "password", unique = true, nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerGame> games;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PlayerGame> getGames() {
        return games;
    }

    public void addGame(Game game) {
        PlayerGame playerGame = new PlayerGame(this, game);
        games.add(playerGame);
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (login != null ? !login.equals(player.login) : player.login != null) return false;
        if (password != null ? !password.equals(player.password) : player.password != null) return false;
        if (games != null ? !games.equals(player.games) : player.games != null) return false;
        return rank != null ? rank.equals(player.rank) : player.rank == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (games != null ? games.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }
}
