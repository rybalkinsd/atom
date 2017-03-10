package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public abstract class TemporaryGameObject extends PositionableGameObject implements Temporary  {
    protected long lifetimeMillis;

    public TemporaryGameObject(Point position, long lifetimeMillis) {
        super(position);
        this.lifetimeMillis = lifetimeMillis;
    }

    public TemporaryGameObject(int x, int y, long lifetimeMillis) {
        super(x, y);
        this.lifetimeMillis = lifetimeMillis;
    }

    /**
     * @return lifetime in milliseconds
     */
    public long getLifetimeMillis() {
        return lifetimeMillis;
    }

    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     * @return true if GameObject is dead
     */
    public boolean isDead() {
        return lifetimeMillis <= 0;
    }

    @Override
    public void tick(long elapsed) {
        if (!isDead()) {
            lifetimeMillis -= elapsed;
        }
    }
}
