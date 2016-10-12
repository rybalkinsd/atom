package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class Cell {

    @NotNull
    private static final Logger log = LogManager.getLogger(Cell.class);

    @NotNull
    private String name;

    @NotNull
    private Color color;

    private int mass = GameConstants.CELL_MASS;

    private double speed = 10D * Math.pow(mass, -1.0D);

    @NotNull
    private Position position;

    public Cell(@NotNull String name, @NotNull Color color, @NotNull Position position) {
        this.name = name;
        this.color = color;
        this.position = position;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(Object cell) {
        if (this == cell) return true;
        if (cell == null || this.getClass() != cell.getClass()) return false;

        Cell currentCell = (Cell) cell;
        return name.equals(currentCell.name) &&
                color.equals(currentCell.color) &&
                position.equals(currentCell.position);
    }

    @Override
    public int hashCode() {
        Random random = new Random();
        return random.hashCode();
    }

    @Override
    public String toString() {
        return "Cell(" +
                "name=" + name +
                ", color=" + color +
                ", mass=" + mass +
                ", speed=" + speed +
                ", position=" + position +
                ")";
    }
}
