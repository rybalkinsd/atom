package bomber.matchmaker.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "gs", schema = "bomber")
public class GameSession {

    @Id
    @Column(name = "game", nullable = false)
    private Integer gameId;

    @Column(name = "time", nullable = false)
    private Date date;

    @Column(name = "status")
    private Boolean status;

    public GameSession(Integer gameId, Date date) {
        this.gameId = gameId;
        this.date = date;
    }

    public GameSession() {

    }

    public Integer getGameId() {
        return gameId;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getStatus() {
        return status;
    }

    public GameSession setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    public GameSession setDate(Date date) {
        this.date = date;
        return this;
    }

    public GameSession setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "GameServer: " + gameId + ", time=" + date + ", status" + status + "\n";
    }
}


