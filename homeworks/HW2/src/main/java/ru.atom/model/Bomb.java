package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ruslbizh on 11.03.2017.
 */
public class Bomb implements Positionable, Temporary {

    private static final Logger log = LogManager.getLogger(Girl.class);
    private int id;
    private Point position;
    private int speed;
    private int elapsedTime;
    private final int lifeTime;

    public Bomb(int x, int y, int lifeTime) {
        if (lifeTime <= 0) {
            log.error("LifeTime must be positive");
            throw new IllegalArgumentException();
        }
        this.position = new Point(x, y);
        this.lifeTime = lifeTime;
        this.id = GameSession.createObjecId();
        this.elapsedTime = 0;
        log.info("Bomb was created with parameters: id = {}, position = ({}, {}), speed = {}, lifeTime = {}",
                id, position.getX(), position.getY(), this.speed, this.lifeTime);
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
