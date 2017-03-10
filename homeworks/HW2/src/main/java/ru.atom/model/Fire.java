package ru.atom.model;

/**
 * Created by Юля on 10.03.2017.
 */
public class Fire extends ObjectPosition implements Temporary {

    long lifeTimeFire = 1;
    long timeFromStartFire = 0;


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
