package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 09.03.17.
 */
public class Bomb implements Temporary, Positionable {
    private Point Position;
    private  int BombId;
    private  long LifetimeMs;
    private final long TimeBeforeExplosion = 2000;

    public  Bomb(Point pos) {
        this.Position = pos;
        this.LifetimeMs = 0;
        BombId = GameSession.getId();
    }
    @Override
    public int getId() {
        return BombId;
    }

    @Override
    public Point getPosition() {
        return Position;
    }

    @Override
    public long getLifetimeMillis() {
        return TimeBeforeExplosion;
    }

    @Override
    public boolean isDead() {
        return LifetimeMs > TimeBeforeExplosion;
    }

    @Override
    public void tick(long elapsed) {
        LifetimeMs = LifetimeMs + elapsed;
    }
}
