package ru.atom.model;

import ru.atom.geometry.Point;

public class Fire implements Temporary, Positionable, Tickable {
    private Point position;
    private static final int LIFETIME = 300;
    private int elapsedTime;
    private final int id;

    public Fire(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.nextId();
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return LIFETIME;
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
    public boolean isDead() {
        return elapsedTime > LIFETIME;
    }
}
