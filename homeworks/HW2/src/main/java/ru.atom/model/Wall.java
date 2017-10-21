package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public final class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);

    private final int id;
    private final Point position;

    public Wall(final Point position) {
        this.position = position;
        this.id = GameSession.nextId();
        log.info("New Wall: id={},  id={}, position({}, {})\n", id, position.getX(), position.getY());
    }


    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
