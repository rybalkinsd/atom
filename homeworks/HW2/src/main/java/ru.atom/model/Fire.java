package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Fire extends ObjectPosition implements Temporary {

    private final long lifeTimeFire = 1;
    private long timeFromStartFire = 0;

    public Fire(int x, int y) {
        super();
        setPosition(new Point(x, y));
    }


    @Override
    public void tick(long elapsed) {
        timeFromStartFire += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeFire;
    }

    @Override
    public boolean isDead() {
        return lifeTimeFire <= timeFromStartFire;
    }
}
