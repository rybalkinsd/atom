package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ilysk on 08.03.17.
 */
public class Bomb implements Temporary, Positionable {

    private final Point position;
    private final int id;
    private final long lifetime;
    private long age;

    public Bomb(int x, int y, long lifetimeMillis) {
        this.position = new Point(x,y);
        this.lifetime = lifetimeMillis;
        this.id = GameSession.createUniqueId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        this.age += elapsed;
        if (this.age > this.lifetime) {
            this.age = this.lifetime;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        if (this.age == this.lifetime) {
            this.makeBoom();
            return true;
        }
        return false;
    }

    private void makeBoom() {
        // пока тут пусто
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
