package ru.atom.model;

import ru.atom.geometry.Point;
import sun.awt.LightweightFrame;

/**
 * Created by Auerbah on 10.03.2017.
 */
public abstract class TemporaryGameObject extends PositionableGameObject implements Temporary  {
    protected long LifetimeMillis;

    public TemporaryGameObject(Point position, long LifetimeMillis) {
        super(position);
        this.LifetimeMillis = LifetimeMillis;
    }

    public TemporaryGameObject(int x, int y, long LifetimeMillis) {
        super(x, y);
        this.LifetimeMillis = LifetimeMillis;
    }

    /**
     * @return lifetime in milliseconds
     */
    public long getLifetimeMillis() {
        return LifetimeMillis;
    }

    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     * @return true if GameObject is dead
     */
    public boolean isDead() {
        return LifetimeMillis <= 0;
    }

    @Override
    public void tick(long elapsed) {
        if(!isDead()) {
            LifetimeMillis -= elapsed;
        }
    }
}
