package ru.atom.model;

import ru.atom.geometry.Point;

public class Bomb implements Positionable, Temporary {
    private Point position;
    private final int id;
    private int strength;
    private boolean isDead = false;
    private static final int LIFETIME = 2000;
    private long elapsedTime = 0;

    public Bomb(int x, int y, int strength) {
        this.position = new Point(x, y);
        this.strength = strength;
        this.id = GameSession.nextId();
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
    public long getLifetimeMillis() {
        return LIFETIME;
    }

    @Override
    public boolean isDead() {
        long temp = elapsedTime;
        if (elapsedTime >= LIFETIME) {
            elapsedTime = 0;
        }
        return temp >= LIFETIME;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }
}
