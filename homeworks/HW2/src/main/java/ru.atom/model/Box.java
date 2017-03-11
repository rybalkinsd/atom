package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by alex on 10.03.17.
 */
public class Box implements Positionable {

    private static final Logger logger = LogManager.getLogger(Box.class);
    private final int id;
    private final Point position;

    public Box(Point position) {
        this.id = GameSession.setGameObjectId();
        this.position = position;
        logger.info("Box is created: id = {} x = {} y = {}", getId(), position.getX(), position.getY());
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }
}
