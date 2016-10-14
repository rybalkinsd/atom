package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.*;

public class Cell extends GameUnit {

    @NotNull
    private static final Logger log = LogManager.getLogger(Cell.class);
    @NotNull
    private String playerName;
    private int cellNumber;

    public Cell(@NotNull Color color, @NotNull Location location, @NotNull String playerName, int cellNumber) {
        super(color, location, INITIAL_CELL_MASS);
        this.playerName = playerName;
        this.cellNumber = cellNumber;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Cell(@NotNull Location location, @NotNull String playerName, int cellNumber) {
        super(location,  INITIAL_CELL_MASS);
        this.playerName = playerName;
        this.cellNumber = cellNumber;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != Cell.class) return false;
        Cell cell = (Cell) object;
        return ((this.playerName == cell.playerName) && (this.cellNumber == cell.cellNumber));
    }

    @Override
    public int hashCode() {
        return this.playerName.hashCode() + this.cellNumber * 7;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "playerName=" + this.playerName +
                ", cellNumber=" + this.cellNumber +
                ", color=" + this.getColor() +
                ", location=" + this.getLocation() +
                ", mass=" + this.getSpeed() +
                ", speed=" + this.getSpeed() +
                ", radius=" + this.getRadius() +
                '}';
    }
}
