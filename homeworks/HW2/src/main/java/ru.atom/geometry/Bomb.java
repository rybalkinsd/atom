package ru.atom.geometry;

import ru.atom.model.Temporary;
import ru.atom.model.GameObject;
import ru.atom.model.Positionable;
import ru.atom.model.GameSession;

public class Bomb implements GameObject, Positionable, Temporary {
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
    public Point getPosition() {
        return position;
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
    public void tick(long elapsed) {
        workTimeMs += elapsed;
        if (workTimeMs >= lifeTimeMs) {
            isDead = true;
        }
    }
}





