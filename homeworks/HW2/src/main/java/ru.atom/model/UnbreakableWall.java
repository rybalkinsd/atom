package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by home on 10.03.2017.
 */
public class UnbreakableWall extends AbstractGameObject implements Positionable {
    private static final Logger log = LogManager.getLogger(UnbreakableWall.class);

    public UnbreakableWall(Point position) {
        super(position.getX(), position.getY());
        log.info("Unbreakable wall was set id={}, x={}, y={}", getId(), position.getX(), position.getY());
    }
}
