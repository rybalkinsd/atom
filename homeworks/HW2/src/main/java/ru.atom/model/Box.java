package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Box implements Positionable {
    private static final Logger log = LogManager.getLogger(Box.class);
    private final int id;
    private Point point;

    public Box(int id, Point point) {
        this.id = id;
        this.point = point;
        log.info("Box with id = " + id + " point = " + point);
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
