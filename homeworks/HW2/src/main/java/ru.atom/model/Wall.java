package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by zarina on 14.03.17.
 */
public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private final int id;
    private Point position;

    public Wall(Point position) {
        this.id = GameSession.createId();
        this.position = position;
        log.info("create object id={}, x={}, y={}", id, position.getX(), position.getY());
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
