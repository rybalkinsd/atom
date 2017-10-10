package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb extends GameObject implements Tickable {
    private static final Logger logger = LogManager.getLogger(Bomb.class);
    private static final int LIFETIME = 3000;

    public Bomb(GameSession session, Point position) {
        super(session, position);
        logger.info("New Bomb id={}, position={}", getId(), position);
    }

    @Override
    public void tick(int elapsed) {
        throw new UnsupportedOperationException();
    }
}
