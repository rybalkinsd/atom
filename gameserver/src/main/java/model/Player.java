package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Server player avatar
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull
    private String name;

    @NotNull
    private List<Cell> cells = new ArrayList<>(GameConstants.MAX_CELLS);

    @NotNull
    private GameStatistics gameStatistics;

    private int score = 16;

    /**
     * Create new Player
     *
     * @param name visible name
     */
    public Player(@NotNull String name) {
        this.name = name;
        Cell startingCell = new Cell(Color.BLUE, new Position(23, 4353));
        this.cells.add(startingCell);
        this.gameStatistics = new GameStatistics();
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(@NotNull List<Cell> cells) {
        this.cells = cells;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cells=" + cells +
                ", gameStatistics=" + gameStatistics +
                ", score=" + score +
                '}';
    }
}
