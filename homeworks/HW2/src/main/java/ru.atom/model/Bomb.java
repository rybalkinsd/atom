package ru.atom.model;

import ru.atom.geometry.Point;

public class Bomb implements Positionable, Temporary {
    private final int id;
    private final Point position;
    private final long lifeTime;
    private long timePassed = 0;

    public Bomb(Point position, long lifeTime) {
        if (lifeTime <= 0) throw new IllegalArgumentException();
        this.id = GameSession.setGameObjectId();
        this.position = position;
        this.lifeTime = lifeTime;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        timePassed += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTime;
    }

    @Override
    public boolean isDead() {
        return timePassed > lifeTime;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
