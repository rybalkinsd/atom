package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BonusExplosion implements Bonus{

    private static final Logger log = LogManager.getLogger(BonusExplosion.class);
    private final int rangeExplosion;
    private Point position;
    private final int id;

    public BonusExplosion(int x, int y) {
        position = new Point(x, y);
        id = GameSession.nextId();
        rangeExplosion = 1;
        log.info("New BonusExplosion id=" + id + ", position=(" + x + "," + y + ")\n");
    }

    @Override
    public int getUpgrade() {
        return rangeExplosion;
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
