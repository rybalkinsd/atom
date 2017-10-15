package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Brick implements Positionable {
    private static final Logger log = LogManager.getLogger(Brick.class);
    private Point position;
    private int id;

    public Brick(Point position,int id) {
        this.position = position;
        this.id = id;
        log.info("Brick with id={} on position({},{})",id,position.getX(),position.getY());
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
