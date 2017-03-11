package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Vlad on 11.03.2017.
 */
public class Fire extends AbstractGameObject implements Temporary {
    private long lifetimeMillis = 1000;

    public Fire(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        lifetimeMillis -= elapsed;
        if (lifetimeMillis < 0) {
            lifetimeMillis = 0;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return lifetimeMillis;
    }

    @Override
    public boolean isDead() {
        return lifetimeMillis == 0;
    }
}
