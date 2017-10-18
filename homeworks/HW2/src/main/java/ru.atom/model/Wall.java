package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall implements Positionable {

    private static final Logger logger = LogManager.getLogger(Wall.class);
    private final int id;
    private final Point position;

    public Wall(Point position, int id) {
        this.position = position;
        this.id = id;
        logger.info("Wall is created: id = {} x = {} y = {}", id, position.getX(),position.getY());
    }

    public int getId() {

        return  id;
    }

    public Point getPosition() {

        return position;
    }
}
