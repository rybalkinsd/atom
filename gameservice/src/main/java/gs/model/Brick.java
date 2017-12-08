package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Brick extends GameObject {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Brick.class);
    private static final int BRICK_WIDTH = 32;
    private static final int BRICK_HEIGHT = 32;

    public Brick(GameSession session, Point position) {
        super(session, position, BRICK_WIDTH, BRICK_HEIGHT);
        logger.info("New Brick id={}, position={}, session_ID={}", id, position, session.getId());
    }
}
