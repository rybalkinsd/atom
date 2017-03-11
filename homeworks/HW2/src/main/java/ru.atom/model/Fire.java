package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Antonio on 11.03.2017.
 */
public class Fire implements Positionable, Temporary {
    private Point position;
    private final int id;
    private static final int lifeTime = 100;
    private long BurningdTime = 0;
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
        BurningdTime += elapsed;

    }

    @Override
    public long getLifetimeMillis() {

        return lifeTime;
    }

    @Override
    public boolean isDead() {

        return BurningdTime > lifeTime;
    }

    @Override
    public Point getPosition() {

        return position;
    }
}
