package ru.atom.model;

import ru.atom.geometry.Point;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Box implements GameObject, Positionable {
    private final int id;
    private Point position;
    private static final Logger log = LogManager.getLogger(Player.class);

    public Box(int id, Point p) {
        this.id = id;
        this.position = new Point(p.getX(), p.getY());
        log.info("new Box has been created");
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}