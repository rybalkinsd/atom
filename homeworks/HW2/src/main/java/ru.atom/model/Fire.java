package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Antonio on 11.03.2017.
 */
public class Fire implements Positionable, Temporary {
    private Point position;
    private final int id;
    private static final int lifeTime = 100;
    private long burningdTime = 0;
    private boolean idDead = false;

    public Fire(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        burningdTime += elapsed;

    }

    @Override
    public long getLifetimeMillis() {

        return lifeTime;
    }

    @Override
    public boolean isDead() {

        return burningdTime > lifeTime;
    }

    @Override
    public Point getPosition() {

        return position;
    }
}
