package ru.atom.model;

public class Bomb extends PositionableObject implements Temporary {

    long lifetime = 10;
    long timePassed = 0;

    @Override
    public void tick(long elapsed) {
        timePassed += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        return timePassed >= lifetime;
    }

}
