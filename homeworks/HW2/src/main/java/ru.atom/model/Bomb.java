package ru.atom.model;


import ru.atom.geometry.Point;

/**
 * Created by ikozin on 10.03.17.
 */
public class Bomb implements Positionable, Temporary {

    private final int id;
    private Point position;
    private long elapsed;
    private static final long LIFETIME = 42; //lifetime of all bombs
    private boolean isDead;

    public Bomb(Point position) {
        id = GameSession.getGameObjectId();
        this.position = position;
        elapsed = 0;
        isDead = false;
    }

    @Override
    public void tick(long elapsed) {
        this.elapsed += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return LIFETIME;
    }

    @Override
    public boolean isDead() {
        if (elapsed <= LIFETIME) {
            return false;
        } else if (!isDead) {
            explode();
            isDead = true;
        }
        return true;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    private void explode() {
        //some explosive activity
    }
}
