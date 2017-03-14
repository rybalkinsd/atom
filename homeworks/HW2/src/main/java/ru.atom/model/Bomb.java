package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by zarina on 14.03.17.
 */
public class Bomb implements Temporary, Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point position;
    private long lifeTime;

    public Bomb(Point position, long lifeTime) {
        if (lifeTime <= 0) {
            throw new IllegalArgumentException("Can't support negative lifeTime");
        }
        this.id = GameSession.createId();;
        this.position = position;
        this.lifeTime = lifeTime;
        log.info("create object id={}, x={}, y={}, lifeTime={}", id, position.getX(), position.getY(),
                lifeTime);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime -= elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTime;
    }

    @Override
    public boolean isDead() {
        return lifeTime <= 0;
    }
}
