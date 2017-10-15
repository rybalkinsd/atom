package ru.atom.model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.atom.geometry.Point;


public class Bomb implements Tickable, Positionable {

    private static final Logger logger = LogManager.getLogger(Bomb.class);

    private final int id;
    private final Point position;
    private long lifetime;
    private final int power;

    public Bomb(int id, Point position, long lifetime, int power) {
        this.id = id;
        this.position = position;
        this.lifetime = lifetime;
        this.power = power;

        logger.info(toString());
    }

    public boolean isDead() {
        return lifetime <= 0;
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
            //an explosion must be created
        }
    }

    @Override
    public String toString() {
        return "[Bomb: id=" + String.valueOf(id)
                + " pos=" + position.toString()
                + " pow=" + String.valueOf(power) + "]";
    }
}
