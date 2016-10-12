package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Position {

    @NotNull
    private static final Logger log = LogManager.getLogger(Position.class);

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(Object position) {
        if (this == position) return true;
        if (position == null || this.getClass() != position.getClass()) return false;

        Position currentPosition = (Position) position;
        return Double.compare(x, currentPosition.x) == 0 &&
                Double.compare(y, currentPosition.y) == 0;
    }

    @Override
    public int hashCode() {
        Random random = new Random();
        return random.hashCode();
    }

    @Override
    public String toString() {
        return "Position(" + "x=" + String.format("%(.2f", x) +
                ", y=" + String.format("%(.2f", y) +
                ')';
    }
}
