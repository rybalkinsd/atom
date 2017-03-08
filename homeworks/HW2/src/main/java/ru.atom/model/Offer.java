package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Western-Co on 08.03.2017.
 */
public class Offer implements Temporary {
    private int id;
    private Point position;
    private static final long OFFER_LIFE_TIME = 7000;
    private long elapsedTime = 0L;
    private Type offerType = Type.SPEED;

    public enum Type {
        SPEED, STRENGTH
    }

    public Offer(Point position, Type offerType) {
        this.id = GameSession.setObjectId();
        this.position = position;
        this.elapsedTime = 0L;
        this.offerType = offerType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        this.elapsedTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return OFFER_LIFE_TIME;
    }

    @Override
    public boolean isDead() {
        if (this.elapsedTime >= OFFER_LIFE_TIME) {
            return true;
        }
        return false;
    }
}
