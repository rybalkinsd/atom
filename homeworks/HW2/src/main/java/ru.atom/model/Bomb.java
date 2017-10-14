package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Tickable, Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private long lifeTime;
    private Point point;

    public Bomb(int id, long lifeTime, Point point) {
        this.id = id;
        this.lifeTime = lifeTime;
        this.point = point;
        log.info("Bomb with id = " + id + " point = " + point);
    }

    @Override
    public void tick(long elapsed) {
        lifeTime -= elapsed;
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return id;
    }
}
