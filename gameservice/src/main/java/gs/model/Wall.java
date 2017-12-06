package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Wall extends GameObject {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Wall.class);
    private static final int WALL_WIDTH = 32;
    private static final int WALL_HEIGHT = 32;

    public Wall(GameSession session, Point position) {
        super(session, position, WALL_WIDTH, WALL_HEIGHT);
        logger.info("New Wall id={}, position={}, session_ID = {}", id, position, session.getId());
    }
}