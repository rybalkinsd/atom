package ru.atom.model;

import ru.atom.geometry.Point;


public class Fire implements Positionable, Temporary {

    private Point position;
    private final int id;
    private static final int lifeTime = 200;
    private long elapsedTime = 0;
    private boolean isDead = false;

    public Fire(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
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
        return lifeTime;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public boolean isDead() {
        return elapsedTime > lifeTime;
    }
}
