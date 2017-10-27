package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements GameObject, Tickable, Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private Point position;
    private int id;

    public Bomb(Point position, int id) {
        this.position = position;
        this.id = id;
        log.info("Bomb with id={} on position({},{})", id, position.getX(), position.getY());
    }

    public void tick(long elapsed) {
        log.info("tick = ", elapsed);
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
