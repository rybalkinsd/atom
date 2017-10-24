package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public final class Box implements Positionable {
    private static final Logger log = LogManager.getLogger(Box.class);
    private final int id;
    private final Point position;

    public Box(final Point position) {
        this.id = GameSession.nextId();
        this.position = position;
        log.info("New Box: id={},  id={}, position({}, {})\n", id, position.getX(), position.getY());
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
