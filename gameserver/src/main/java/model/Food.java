package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class Food {

    @NotNull
    private static final Logger log = LogManager.getLogger(Food.class);

    @NotNull
    private Position position;

    @NotNull
    private Color color;

    int mass = GameConstants.FOOD_MASS_UNIT;

    public Food(@NotNull Position position) {
        this.color = Color.getHSBColor(new Random().nextFloat(),
                new Random().nextFloat(), new Random().nextFloat());
        this.position = position;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(Object foods) {
        if (this == foods) return true;
        if (foods == null || this.getClass() != foods.getClass()) return false;

        Food currentFood = (Food) foods;
        return position.equals(currentFood.position);
    }

    @Override
    public int hashCode() {
        Random random = new Random();
        return random.hashCode();
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
