package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by ruslbizh on 11.03.2017.
 */
public class Explosion implements Positionable, Temporary {

    private static final Logger log = LogManager.getLogger(Girl.class);
    private int id;
    private Point position;
    private int elapsedTime;
    private int lifeTime;

    public Explosion(int x, int y, int lifeTime) {
        if (lifeTime <= 0) {
            log.error("LifeTime must be positive");
            throw new IllegalArgumentException();
        }
        this.position = new Point(x, y);
        this.lifeTime = lifeTime;
        this.id = GameSession.createObjecId();
        this.elapsedTime = 0;
        log.info("Explosion was created with parameters: id = {}, position = ({}, {}), lifeTime = {}",
                id, position.getX(), position.getY(), this.lifeTime);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTime;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public boolean isDead() {
        return elapsedTime > lifeTime;
    }
}
