package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.*;

import static model.GameConstants.INITIAL_CELL_MASS;
import static model.GameConstants.MAX_CELLS_AMOUNT;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private User user;
    @NotNull
    private Set<Cell> cells = new HashSet<>(MAX_CELLS_AMOUNT);
    private int cellsEaten = 0;
    private int foodEaten = 0;
    private long initialTime;
    private int score = 16;

    /**
     * Create new Player
     *
     * @param name visible name
     */
    public Player(@NotNull String name, @NotNull User user) {
        this.user = user;
        this.id = UUID.randomUUID();
        this.name = name;
        this.initialTime = System.currentTimeMillis();
        Cell startingCell = new Cell(new Location(),name,0);
        this.cells.add(startingCell);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Player(@NotNull User user) {
        this.user = user;
        this.id = UUID.randomUUID();
        this.name = user.getUserName();
        this.initialTime = System.currentTimeMillis();
        Cell startingCell = new Cell(new Location(),name,0);
        this.cells.add(startingCell);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public Set<Cell> getCells () {
        return this.cells;
    }

    @NotNull
    public UUID getId() {
        return this.id;
    }

    @NotNull
    public int getMass() {
        int mass = 0;
        for (Cell elem :
                this.getCells()) {
            mass += elem.getMass();
        }
        return mass;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Player.class) return false;
        Player player = (Player) object;
        return this.id == player.id;
    }

    @Override
    public int hashCode() {
        int k = 7;
        int sum = 0;
        for (int i = 0; i < this.name.length(); i++) {
            sum = +k * this.name.charAt(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cells=" + this.getCells() +
                ", mass=" + this.getMass() +
                ", foodEaten=" + foodEaten +
                ", cellsEaten=" + cellsEaten +
                ", initialTime=" + initialTime +
                ", score=" + score +
                '}';
    }
}
