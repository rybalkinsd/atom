package ru.atom.model;


import ru.atom.geometry.Point;

public class Bomb extends AbstractGameObject implements Temporary {
    private long leftLifeTimeMs;

    public Bomb(int x, int y, long lifeTimeMs) {
        super(x, y);
        leftLifeTimeMs = lifeTimeMs;
    }

    @Override
    public void tick(long elapsed) {
        leftLifeTimeMs -= elapsed;
        if (leftLifeTimeMs < 0) leftLifeTimeMs = 0;
    }

    @Override
    public long getLifetimeMillis() {
        return leftLifeTimeMs;
    }

    @Override
    public boolean isDead() {
        if (leftLifeTimeMs == 0) {
            explode();
            pos = null;
        }
        return pos == null;
    }

    private void explode() {

    }
}
