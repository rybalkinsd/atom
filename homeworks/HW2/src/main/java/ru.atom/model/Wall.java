package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements Positionable {

    private int id;
    private Point position;
    final Logger log = LogManager.getLogger(GameSession.class);

    public Wall(int id, Point position) {
        this.id = id;
        this.position = position;
        log.info("Wall with ID(" + id + ") was created on point("
                + position.getX() + "," + position.getY() + ")");
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
