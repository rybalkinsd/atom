package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bomb implements Positionable, Temporary {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private final Point position;
    private long lifetime;
    private int power;
    private long elapsedTime;

    public Bomb(int x, int y, int id, int power) {
        if (x < 0 || y < 0 || id < 0) {
            log.error("Invalid arguments");
            throw new IllegalArgumentException();
        }
        this.position = new Point(x, y);
        this.id = id;
        this.lifetime = 4000;
        this.elapsedTime = 0L;
        this.power = power;
        log.info("Bomb(id = {}) was created in ( {} ; {} ) with lifeTime: {} and power {}", id,
                position.getX(), position.getY(), this.lifetime, this.power);
    }

    @Override
    public void tick(long elapsedTime) {
        this.elapsedTime += elapsedTime;
    }

    @Override
    public boolean isDead() {
        return this.elapsedTime >= this.lifetime;
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
    public long getLifetimeMillis() {
        return this.lifetime;
    }
}