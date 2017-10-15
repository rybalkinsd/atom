package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Explosion implements Tickable, Positionable {

    private static final Logger logger = LogManager.getLogger(Explosion.class);

    private final int id;
    private final Point position;
    private long lifetime;

    public Explosion(int id, Point position, long lifetime) {
        this.id = id;
        this.position = position;
        this.lifetime = lifetime;

        logger.info(toString());
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        lifetime -= elapsed;
        if (lifetime <= 0) {
            //this explosion must be removed
        }
    }

    @Override
    public String toString() {
        return "[Explosion: id=" + String.valueOf(id) + " pos=" + position.toString() + "]";
    }
}
