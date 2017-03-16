package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BonusCountBomb implements Bonus {

    private static final Logger log = LogManager.getLogger(BonusCountBomb.class);
    private final int id;
    private Point position;
    private final int countBomb;

    public BonusCountBomb(int x, int y) {
        id = GameSession.nextId();
        position = new Point(x, y);
        countBomb = 1;
        log.info("New BonusCountBomb id=" + id + ", position=(" + x + "," + y + ")\n");
    }

    @Override
    public int getUpgrade() {
        return countBomb;
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
