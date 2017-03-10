package ru.atom.model;

import ru.atom.geometry.Point;

public class Bomb implements Positionable, Temporary {

    private final int id;
    private final Point position;
    private long lifetime;
    private long eclepsedTime = 0;

    public Bomb(Point position, long lifetime) {
        this.id = GameSession.setGameObjectId();
        this.position = position;
        this.lifetime = lifetime;
    }

    @Override
    public void tick(long eclepsedTime) {
        this.eclepsedTime += eclepsedTime;
    }

    @Override
    public boolean isDead() {
        return this.eclepsedTime > this.lifetime;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public long getLifetimeMillis() {
        return this.lifetime;
    }
}