package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Western-Co on 08.03.2017.
 */
public class Bomb implements Temporary {
    private int id;
    private Point position;
    private static final long BOMB_LIFE_TIME = 1500;
    private long elapsedTime;

    public Bomb(Point position) {
        this.id = GameSession.setObjectId();
        this.position = position;
        this.elapsedTime = 0L;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        this.elapsedTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return BOMB_LIFE_TIME;
    }

    @Override
    public boolean isDead() {
        if (elapsedTime >= BOMB_LIFE_TIME) {
            return true;
        }
        return false;
    }
}
