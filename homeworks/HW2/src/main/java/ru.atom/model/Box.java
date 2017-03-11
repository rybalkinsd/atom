package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ruslbizh on 11.03.2017.
 */
public class Box implements Positionable {

    private static final Logger log = LogManager.getLogger(Box.class);
    private int id;
    private Point position;

    public Box(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.createObjecId();
        log.info("Box was created with parameters: id = {}, position = ({}, {})", id, position.getX(), position.getY());
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
