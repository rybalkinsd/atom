package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Burned boxes
 */
public class Box implements Positionable, Temporary {

    private Point position;
    private final int id;
    private static final int lifeTime = 5000;
    private long elapsedTime = 0;
    private boolean isDead = false;

    public Box(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
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
    public boolean isDead() {
        return elapsedTime > lifeTime;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
