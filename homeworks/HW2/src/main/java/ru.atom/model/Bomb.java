package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 09.03.17.
 */
public class Bomb implements Temporary, Positionable {
    private Point position;
    private  int bombId;
    private  long lifetimeMs;
    private final long timeBeforeExplosion = 2000;

    public  Bomb(Point pos) {
        this.position = pos;
        this.lifetimeMs = 0;
        bombId = GameSession.getId();
    }

    @Override
    public int getId() {
        return bombId;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public long getLifetimeMillis() {
        return timeBeforeExplosion;
    }

    @Override
    public boolean isDead() {
        return lifetimeMs > timeBeforeExplosion;
    }

    @Override
    public void tick(long elapsed) {
        lifetimeMs = lifetimeMs + elapsed;
    }
}
