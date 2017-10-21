package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public final class Bonus implements Positionable {
    private static final Logger log = LogManager.getLogger(Bonus.class);
    private final int id;
    private final Point position;
    private final Type type;

    public enum Type {
        SPEED, BOMB, RANGE
    }

    public Bonus(final Point position, final Type type) {
        this.id = GameSession.nextId();
        this.position = position;
        this.type = type;
        log.info("New Bonus: id={}, position({}, {}), type={}\n", id, position.getX(), position.getY(), type);
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
