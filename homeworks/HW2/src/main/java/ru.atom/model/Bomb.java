package ru.atom.mode1;

import ru.atom.geometry.Point;

public class Bomb implements Temporary, Positionable  {
    private int id;
    private Point position;
    private static final long bomb_lifetime = 1000;
    private long elapsedTime;

    public Bomb(Point position) {
        this.id = Gamesession.SetObjectId();
        this.position = position;
        this.elapsedTime =0L;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public long getLifetimeMillis() {
        returm bomb_lifetime;
    }

    @Override
    public boolean isDead() {
        if(elapsedTime >= bomb_lifetime) {
            return true;
        }
        return false;
    }
}