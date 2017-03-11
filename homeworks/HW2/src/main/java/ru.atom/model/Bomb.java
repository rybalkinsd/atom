package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Antonio on 11.03.2017.
 */
public class Bomb implements Positionable, Temporary {
    private Point position;
    private final int id;
    private static final int blasttime = 200;
    private long bombtimer = 0;
    private boolean isDead = false;

    public Bomb(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
    }

    @Override
    public int getId() {

        return id;
    }

    @Override
    public void tick(long elapsed) {
        bombtimer += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return blasttime;
    }

    @Override
    public boolean isDead() {

        return bombtimer > blasttime;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
