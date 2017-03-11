package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ruslbizh on 11.03.2017.
 */
public class Wall implements Positionable {

    private static final Logger log = LogManager.getLogger(Box.class);
    private int id;
    private Point position;

    public Wall(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.createObjecId();
        log.info("Wall was created with parameters: id = {}, position = ({}, {})",
                id, position.getX(), position.getY());
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
