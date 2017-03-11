package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class UnbreackableWall extends AbstractGameObject implements Positionable {
    private static final Logger log = LogManager.getLogger(UnbreakableWall.class);

    public UnbreakableWall(Point position) {
        super(position.getX(), position.getY());
        log.info("New unbreakable wall is set. id={}, x={}, y={}", getId(), position.getX(), position.getY());
    }
}