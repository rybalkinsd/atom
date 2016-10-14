package model.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Cell;
import model.GameConstants;
import model.GameStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Server player avatar
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull private UUID playerId;
    @NotNull private String login;
    @NotNull private String password;
    @NotNull private String name;

    @NotNull
    private List<Cell> cells = new ArrayList<>(GameConstants.MAX_CELLS);

    @NotNull
    private GameStatistics gameStatistics;

    private int score = 16;

    /**
     * Create new Player
     *
     * @param login Player register login
     * @param password Player register password
     */
    public Player(@NotNull String login, @NotNull String password) {
        this.playerId = UUID.randomUUID();
        this.login = login;
        this.password = password;
        name = login;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    @NotNull
    @JsonIgnore
    public List<Cell> getCells() {
        return cells;
    }

    public void addCell(@NotNull Cell cell) {
        cells.add(cell);
    }

    public void addCells(@NotNull List<Cell> cells) {
        this.cells.addAll(cells);
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", cells=" + cells +
                ", gameStatistics=" + gameStatistics +
                ", score=" + score +
                '}';
    }
}
