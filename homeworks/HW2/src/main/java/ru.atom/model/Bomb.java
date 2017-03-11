package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Vlad on 11.03.2017.
 */
public class Bomb extends AbstractGameObject implements Temporary {
    private long lifetimeMillis = 3000;

    public Bomb(int x, int y) {
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
