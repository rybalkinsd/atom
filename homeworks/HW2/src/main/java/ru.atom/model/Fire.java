package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Fire extends GameObject implements Tickable {
    private static final Logger logger = LogManager.getLogger(Fire.class);
    private static final int LIFETIME = 3000;

    public Fire(GameSession session, Point position) {
        super(session, position);
        logger.info("New Fire id={}, position={}", getId(), position);
    }

    @Override
    public void tick(int elapsed) {
        throw new UnsupportedOperationException();
    }
}
