package gamemodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Cell {

    @NotNull
    private static final Logger log = LogManager.getLogger(Cell.class);

    @NotNull
    private final Color color;

    @NotNull
    private Position position;

    private int mass = GameConstants.STARTING_CELL_MASS_VALUE;
    // need some calculations around mass
    private double speed = 10D * Math.pow(mass, -1.0D);
    private double radius = 0.3D * mass;

    public Cell(@NotNull Color color, @NotNull Position position) {
        this.color = color;
        this.position = position;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public Color getColor() {
        return color;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    public int getMass() {
        return mass;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "color=" + color +
                ", position=" + position +
                ", mass=" + mass +
                ", speed=" + speed +
                ", radius=" + radius +
                '}';
    }
}
