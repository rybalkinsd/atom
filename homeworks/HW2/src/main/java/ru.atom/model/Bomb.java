package ru.atom.model;

import ru.atom.geometry.Point;


public class Bomb implements Positionable, Temporary {

    private Point position;
    private int power;
    private final int id;
    private static final int lifeTime = 2400;
    private long elapsedTime = 0;
    private boolean isDead = false;

    public Bomb(int x, int y, int power) {
        position = new Point(x, y);
        this.power = power;
        id = GameSession.idCounter();
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
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
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

}
