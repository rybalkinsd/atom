package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class TemporaryGameObject extends PositionalGameObject implements Temporary {
    private final long lifeTime;
    private long age = 0;

    public TemporaryGameObject(int x, int y, long lifeTime) {
        super(x, y);
        this.lifeTime = lifeTime;
    }

    public TemporaryGameObject(Point position, long lifeTime) {
        super(position);
        this.lifeTime = lifeTime;
    }

    @Override
    public void tick(long elapsed) {
        age += elapsed;

    }

    @Override
    public long getLifetimeMillis() {
        return lifeTime;
    }

    @Override
    public boolean isDead() {
        return age > lifeTime;
    }
}
