package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Obstacle implements Positionable {
    private static final Logger log = LogManager.getLogger(Obstacle.class);
    private Point point;
    private final int id;

    public Obstacle(int id, Point point) {
        this.id = id;
        this.point = point;
        log.info("Box with id = " + id + " coordinates : " + point);
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return id;
    }
}