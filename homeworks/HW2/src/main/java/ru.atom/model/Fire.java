package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Fire extends GameObject implements Tickable {
    private static final Logger logger = LogManager.getLogger(Fire.class);
    private static final int LIFETIME = 200;
    private static final int FIRE_WIDTH = 38;
    private static final int FIRE_HEIGHT = 38;

    private int elapsed = 0;

    public Fire(GameSession session, Point position) {
        super(session, position, FIRE_WIDTH, FIRE_HEIGHT);
        logger.info("New Fire id={}, position={}", id, position);
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
        if (this.elapsed >= LIFETIME) {
            session.removeById(this.id);
        }
    }
}
