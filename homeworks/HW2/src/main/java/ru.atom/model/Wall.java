package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private final int id;
    private final Point position;

    public Wall(int x, int y, int id) {
        if (x < 0 || y < 0 || id < 0) {
            log.error("Invalid arguments");
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.position = new Point(x, y);
        log.info("Wall(id = {}) was created in ( {} ; {} )", id, position.getX(), position.getY());
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }
}