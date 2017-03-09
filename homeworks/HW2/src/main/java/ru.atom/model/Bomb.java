package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by pavel on 06.03.17.
 */
public class Bomb extends AbstractGameObject implements Positionable, Temporary {

    private static final Logger log = LogManager.getLogger(Bomb.class);
    private Point position;
    private int id;
    private boolean isDead;
    private long lifetimeMillis;

    public Bomb(Point position) {
        this.position = position;
        this.isDead = false;
        this.lifetimeMillis = 3000;
        this.id = AbstractGameObject.id++;
        log.info(this + " was created");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        lifetimeMillis = lifetimeMillis - elapsed;
        if (lifetimeMillis < 0) {
            isDead = true;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return lifetimeMillis;
    }

    @Override
    public boolean isDead() {
        if (isDead) {
            log.info(this + " is dead");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Bomb{"
                + "id="
                + getId()
                + '}';
    }
}
