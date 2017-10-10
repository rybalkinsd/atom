package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb extends GameObject implements Tickable {
    private static final Logger logger = LogManager.getLogger(Bomb.class);
    private static final int LIFETIME = 300;
    private static final int WIDTH = 28;
    private static final int HEIGHT = 28;

    private int elapsed = 0;

    public Bomb(GameSession session, Point position) {
        super(session, position);
        logger.info("New Bomb id={}, position={}", id, position);
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
        if (this.elapsed >= LIFETIME) {
            session.removeByPosition(position);
        }
    }
}
