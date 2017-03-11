package ru.atom.model;

import ru.atom.geometry.Point;
/**
 * Created by ilnur on 11.03.17.
 */

public class Bomb implements Positionable, Temporary {
    private Point pos;
    private long age;
    private int id;
    private long lifetime;

    public Bomb(int x, int y, long lifemills) {
        this.pos = new Point(x,y);
        this.lifetime = lifemills;
        this.id = GameSession.createid();
    }



    @Override
    public void tick(long elapsed) {
        this.age += elapsed;

    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        if (this.age >= this.lifetime) {
            return true;
        }
        return false;
    }

    @Override
    public Point getPosition() {
        return pos;
    }

    @Override
    public int getId() {
        return id;
    }
}
