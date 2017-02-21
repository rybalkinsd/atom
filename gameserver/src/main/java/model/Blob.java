package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.BLOB_MASS;

public class Blob extends GameUnit {

    @NotNull
    private static final Logger log = LogManager.getLogger(Blob.class);

    public Blob(@NotNull Color color, @NotNull Location location, double speed) {
        super(color, location, speed, BLOB_MASS);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Blob{" +
                "location=" + this.getLocation() +
                ", color=" + this.getColor() +
                ", speed=" + this.getSpeed() +
                ", mass=" + this.getMass() +
                ", radius=" + this.getRadius() +
                '}';
    }
}
