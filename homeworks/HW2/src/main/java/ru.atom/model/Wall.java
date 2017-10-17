package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements GameObject, Positionable{
    private static final Logger log = LogManager.getLogger(Wall.class);
    private Point position;
    private boolean breakable;
    private int id;

    public Wall(Point position, boolean breakable, int id) {
        this.position = position;
        this.breakable = breakable;
        this.id = id;
        log.info("Wall with id={} on position({},{}), breakable = {}", id, position.getX(), position.getY(), breakable);
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

}
