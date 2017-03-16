package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Wall implements Positionable {

    private static final Logger log = LogManager.getLogger(Wall.class);
    private final int id;
    private Point position;

    public Wall(int x, int y) {
        position = new Point(x, y);
        id = GameSession.nextId();
        log.info("New Wall id=" + id + ", position=(" + x + "," + y + ")\n");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
