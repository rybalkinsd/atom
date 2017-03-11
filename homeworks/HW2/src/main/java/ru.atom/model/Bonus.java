package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bonus implements Positionable, Temporary {
    private static final Logger log = LogManager.getLogger(Bonus.class);
    private final int id;
    private final Point position;
    private long lifetime;
    private long elapsedTime;
    private final Type bonusType;

    public enum Type {
        SPEED,
        BOMBPOWER
    }

    public Bonus(int x, int y, int id, Type type) {
        if (x < 0 || y < 0 || id < 0) {
            log.error("Invalid arguments");
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.position = new Point(x, y);
        this.lifetime = 6000;
        this.elapsedTime = 0L;
        this.bonusType = type;
        log.info("Bonus(id = {}) was created in ( {} ; {} ) with lifetime = {} and type {}", id,
                position.getX(), position.getY(), getLifetimeMillis(), type);
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void tick(long elapsedTime) {
        this.elapsedTime += elapsedTime;
    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        return this.elapsedTime >= this.lifetime;
    }
}