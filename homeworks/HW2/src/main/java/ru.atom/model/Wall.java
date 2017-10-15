package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall extends GameObject {
    private static final Logger logger = LogManager.getLogger(Wall.class);
    private static final int WALL_WIDTH = 32;
    private static final int WALL_HEIGHT = 32;

    public Wall(GameSession session, Point position) {
        super(session, position, WALL_WIDTH, WALL_HEIGHT);
        logger.info("New Wall id={}, position={}", id, position);
    }
}
