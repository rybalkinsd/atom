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
    private Color color;

    private int mass = GameConstants.CELL_MASS;

    private double speed = 10D * Math.pow(mass, -1.0D);

    @NotNull
    private Position position;

    public Cell(@NotNull Color color, @NotNull Position position) {
        this.color = color;
        this.position = position;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Cell(" +
                "color=" + color +
                ", mass=" + mass +
                ", speed=" + speed +
                ", position=" + position +
                ")";
    }
}
