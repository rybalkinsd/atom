package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class Bomb implements Positionable, Temporary {

    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point position;
    private static final long LIFE_TIME = 3000;
    private long lifetime;
    private int rangeExplosion;

    public Bomb(int x, int y) {
        position = new Point(x, y);
        lifetime = 0L;
        this.rangeExplosion = 1;
        id = GameSession.nextId();
        log.info("New Girl id=" + id + ", position=(" + x + "," + y + ")\n");
        log.info("Explosion range: " + rangeExplosion + "\n");
    }

    @Override
    public long getLifetimeMillis() {
        return LIFE_TIME;
    }

    @Override
    public boolean isDead() {
        if (lifetime >= LIFE_TIME) {
            log.info("Bomb exploded!");
            return true;
        } else {
            log.info("Bomb didn't exploded!");
            return false;
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
    }

    @Override
    public int getId() {
        return id;
    }
}
