package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Random;

import static model.GameConstants.FOOD_MASS;

public class Food extends GameUnit {

    @NotNull
    private static final Logger log = LogManager.getLogger(Food.class);

    public Food(@NotNull Color color, @NotNull Location location) {
        super(color, location, FOOD_MASS);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Food(@NotNull Location location) {
        super(location, FOOD_MASS);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != Food.class) return false;
        Food food = (Food) object;
        return (this.getLocation() != food.getLocation());
    }

    @Override
    public int hashCode() {
        return this.getLocation().getX() * 3 + this.getLocation().getY() * 5;
    }

    @Override
    public String toString() {
        return "Food{" +
                "color=" + this.getColor() +
                ", location=" + this.getLocation() +
                ", mass=" + this.getMass() +
                ", radius=" + this.getRadius() +
                '}';
    }
}
