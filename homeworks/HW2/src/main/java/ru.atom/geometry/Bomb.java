package ru.atom.geometry;

import ru.atom.model.GameSession;
import ru.atom.model.Positionable;
import ru.atom.model.Temporary;

/**
 * Created by zxcvbg on 22.03.2017.
 */
public class Bomb implements Positionable, Temporary {
    private final Point position;
    private final long lifeTimeMs = 3000;
    private long workTimeMs = 0;
    private boolean isDead = false;
    private final int id;

    public Bomb(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.getNextId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        workTimeMs = +elapsed;
        if (workTimeMs >= lifeTimeMs) {
            isDead = true;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeMs;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public Point getPosition() {
        return position ;
    }
}