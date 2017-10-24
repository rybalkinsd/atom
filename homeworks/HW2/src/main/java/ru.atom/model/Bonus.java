package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bonus implements Positionable {
    private final Point position;
    private final int bonusId;
    private static final Logger log = LogManager.getLogger(Bonus.class);

    public Bonus(final Point position) {
        this.position = position;
        this.bonusId = GameSession.getBonusId();
        log.info("New bonus was created: id={}, position = ({},{})", bonusId, position.getX(), position.getY());
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return bonusId;
    }
}
