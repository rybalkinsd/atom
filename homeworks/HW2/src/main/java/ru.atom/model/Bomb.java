package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by alex on 10.03.17.
 */

public class Bomb implements Positionable, Temporary {

    private static final Logger logger = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point position;
    private long passedTime = 0;
    private final long lifeTime;

    public Bomb(Point position, long lifeTime) {
        if (lifeTime <= 0) {
            logger.error("Bombs lifeTime must be > 0");
            throw new IllegalArgumentException();
        }
        this.id = GameSession.setGameObjectId();
        this.lifeTime = lifeTime;
        this.position = position;
        logger.info("Bomb is created: id = {} x = {} y = {} lifeTime = {}",
                getId(), position.getX(), position.getY(), getLifetimeMillis());
    }


    public int getId() {
        return id;
    }

    public void tick(long elapsed) {
        passedTime += elapsed;

    }

    public long getLifetimeMillis() {
        return lifeTime;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isDead() {
        return (passedTime > lifeTime);
    }


}
