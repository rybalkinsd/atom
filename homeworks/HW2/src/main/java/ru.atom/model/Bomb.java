package ru.atom.model;

import ru.atom.geometry.Point;

public class Bomb implements Temporary, Positionable, Tickable {

    private int id;
    private Point position;
    private long lifeTime;
    private int explotionRadius; //радиус действия бомбы
    private int currentTime; // текущее время жизни

    public Bomb(int id, Point position) {
        this.id = id;
        this.position = position;
        this.explotionRadius = 1;
        this.lifeTime = 3000;

    }

    @Override
    public void tick(long elapsed) {
        currentTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return currentTime;
    }

    @Override
    public boolean isDead() {
        if (currentTime >= lifeTime) return true;
        else return false;
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
