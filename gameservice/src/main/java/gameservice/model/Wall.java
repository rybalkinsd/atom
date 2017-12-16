package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

public class Wall extends GameObject {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wall.class);

    private static final int WALL_WIDTH = 32;
    private static final int WALL_HEIGHT = 32;

    public Wall(GameSession session, Point position) {
        super(session, new Point(position.getX() * GameObject.getWidthBox(),
                        position.getY() * GameObject.getWidthBox()),
                "Wall", WALL_WIDTH, WALL_HEIGHT);
        log.info("New Wall id={}, position={}, session_ID = {}", id, position, session.getId());
    }
}