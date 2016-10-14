package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class Blob {

    @NotNull
    private static final Logger log = LogManager.getLogger(Blob.class);

    @NotNull
    private Position position;

    @NotNull
    private final Color color;

    private final int mass = GameConstants.BLOB_MASS_VALUE;

    private double speed;
    private final double radius = GameConstants.BLOB_RADIUS;

    public Blob(@NotNull Position position, @NotNull Color color, double speed) {
        this.position = position;
        this.color = color;
        this.speed = speed;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Blob{" +
                "position=" + position +
                ", color=" + color +
                ", mass=" + mass +
                ", speed=" + speed +
                ", radius=" + radius +
                '}';
    }
}
