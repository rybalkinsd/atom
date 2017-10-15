package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private Point position;
    private int id;

    public Wall(Point position,int id) {
        this.position = position;
        this.id = id;
        log.info("Wall with id={} on position({},{})",id,position.getX(),position.getY());
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
