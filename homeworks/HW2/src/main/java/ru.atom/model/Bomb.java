package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Bomb extends ObjectPosition implements Temporary {
    private final long lifeTimeBomb = 10;
    private long timeFromStartBomb = 0;

    public Bomb(int x, int y) {
        super();
        setPosition(new Point(x, y));
    }


    @java.lang.Override
    public void tick(long elapsed) {
        timeFromStartBomb += elapsed;
    }

    @java.lang.Override
    public long getLifetimeMillis() {
        return lifeTimeBomb;
    }

    @java.lang.Override
    public boolean isDead() {
        return lifeTimeBomb <= timeFromStartBomb;
    }
}
