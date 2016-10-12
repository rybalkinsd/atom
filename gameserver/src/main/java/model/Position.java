package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Position {

    @NotNull
    private static final Logger log = LogManager.getLogger(Position.class);

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
