package ru.atom.model;


import ru.atom.geometry.Point;

public class Bomb implements Temporary, Positionable {
    private final int id;
    private final Point position;
    private final long lifeTime;
    private long time = 0L;

    public Bomb(int id, Point position, long lifeTime) {
        this.id = id;
        this.position = position;
        this.lifeTime = lifeTime;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        time += elapsed;

    }

    @Override
    public long getLifetimeMillis() {
        return lifeTime;
    }

    @Override
    public boolean isDead() {
        return (lifeTime <= time);
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
