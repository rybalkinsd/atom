package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Location {
    private static final Logger log = LogManager.getLogger(Player.class);
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Location() {
        Random random = new Random();
        this.x = random.nextInt(Field.WIDTH);
        this.y = random.nextInt(Field.LENGTH);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != Location.class) return false;
        Location location = (Location) object;
        if ((this.getX() == location.getX()) && (this.getY() == location.getY())) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                "y=" + y +
                '}';
    }
}
