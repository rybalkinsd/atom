package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Даша on 06.03.2017.
 */
public class Bomb implements Positionable, Temporary {

    private Point position;
    private static final long LifetimeMillis = 1000;
    private long lifetime;
    private final int id;

    public Bomb(int x, int y) {
        this.position = new Point(x, y);
        this.lifetime = 0;
        this.id = GameSession.nextId();
    }

    @Override
    public long getLifetimeMillis() {
        return LifetimeMillis;
    }

    @Override
    public boolean isDead() {
        return lifetime >= LifetimeMillis;
    }

    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}

