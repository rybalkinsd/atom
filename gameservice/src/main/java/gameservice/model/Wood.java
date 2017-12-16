package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

public class Wood extends GameObject {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wood.class);

    private static final int WOOD_WIDTH = 32;
    private static final int WOOD_HEIGHT = 32;

    public Wood(GameSession session, Point position) {
        super(session, new Point(position.getX() * GameObject.getWidthBox(),
                        position.getY() * GameObject.getWidthBox()),
                "Wood", WOOD_WIDTH, WOOD_HEIGHT);
        log.info("New Wood id={}, position={}, session_ID={}", id, position, session.getId());
    }
}
