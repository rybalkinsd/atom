package gamemodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class Food {

    @NotNull
    private static final Logger LOG = LogManager.getLogger(Food.class);

    @NotNull
    private final Color color;

    @NotNull
    private Position position;

    private final int mass = GameConstants.FOOD_MASS_VALUE;

    private final double radius = 0.3D * mass;

    public Food(@NotNull Color color, @NotNull Position position) {
        this.color = color;
        this.position = position;
        if (LOG.isInfoEnabled()) {
            LOG.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "color=" + color +
                ", position=" + position +
                ", mass=" + mass +
                '}';
    }
}
