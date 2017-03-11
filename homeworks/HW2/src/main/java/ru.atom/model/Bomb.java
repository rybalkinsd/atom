package ru.atom.model;

import ru.atom.geometry.Point;

public class Bomb implements Positionable, Temporary {
    private final int id;

    // Entity position on map grid
    private Point position;

    // How far the fire reaches when bomb explodes
    private final int strength;

    private long timePassedMillis = 0;
    private long lifetimeMillis = 20;
    private boolean exploded = false;
    private Fire[] fires;

    public Bomb(Point position, int strength) {
        this.id = GameSession.getNextId();
        this.position = position;
        this.strength = strength;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        timePassedMillis += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifetimeMillis;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public boolean isDead() {
        return lifetimeMillis < timePassedMillis;
    }
}
