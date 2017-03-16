package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BonusSpeed implements Bonus {

    private static final Logger log = LogManager.getLogger(BonusSpeed.class);
    private final int velocity;
    private Point position;
    private final int id;

    public BonusSpeed(int x, int y) {
        position = new Point(x, y);
        id = GameSession.nextId();
        velocity = 1;
        log.info("New BonusSpeed id=" + id + ", position=(" + x + "," + y + ")\n");
    }

    @Override
    public int getUpgrade() {
        return velocity;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
