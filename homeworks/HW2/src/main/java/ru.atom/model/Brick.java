package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Brick extends GameObject {
    private static final Logger logger = LogManager.getLogger(Brick.class);

    public Brick(GameSession session, Point position) {
        super(session, position);
        logger.info("New Brick id={}, position={}", getId(), position);
    }
}
