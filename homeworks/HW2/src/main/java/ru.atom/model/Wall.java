package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements Positionable {

    private static final Logger logger = LogManager.getLogger(Wall.class);

    private final int id;
    private final Point position;

    public Wall(int id, Point position) {
        this.id = id;
        this.position = position;

        logger.info(toString());
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
    public String toString() {
        return "[Wall: id=" + String.valueOf(id) + " pos=" + position.toString() + "]";
    }
}
