package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public final class  Bomb implements Positionable, Tickable {

    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final Point position;
    private final int id;
    private long lifeTime;
    private final int explosionRange;
    private static final long LIFE_TIME = 1;

    public Bomb(final Point position,final int explosionRange) {
        this.position = position;
        this.explosionRange = explosionRange;
        this.id = GameSession.nextId();
        this.lifeTime = 0;

        log.info("Bomb: id={}, position({}, {})\n", id, position.getX(), position.getY()) ;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime += elapsed;
    }
}
