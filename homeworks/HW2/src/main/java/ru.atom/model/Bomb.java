package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Bomb extends ObjectPosition implements Temporary {
    long lifeTimeBomb = 10;
    long timeFromStartBomb = 0;

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
