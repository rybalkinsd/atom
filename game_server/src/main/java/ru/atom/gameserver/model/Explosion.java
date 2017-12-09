package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

public class Explosion extends AbstractGameObject implements Tickable {

    private long lifetime;

    public Explosion(int id, Point position, long lifetime) {
        super(id, position);
        this.lifetime = lifetime;
    }

    public long getLifetime() {
        return lifetime;
    }

    @Override
    public void tick(long elapsed) {
        lifetime -= elapsed;
        if (lifetime <= 0) {
            //this explosion must be removed
        }
    }
}
