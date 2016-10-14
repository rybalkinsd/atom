package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import server.model.User;
import utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Server player avatar
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull private User user;

    @NotNull
    private List<Cell> cells = new ArrayList<>(GameConstants.MAX_CELLS);

    @NotNull
    private GameStatistics gameStatistics;

    private int score = 16;

    public Player(@NotNull User user) {
        this.user = user;
        Cell startingCell = new Cell(ColorUtils.generateRandomColor(), new Position(123, 321));
        addCell(startingCell);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
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
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + user.getName() + '\'' +
                ", cells=" + cells +
                ", gameStatistics=" + gameStatistics +
                ", score=" + score +
                '}';
    }
}
