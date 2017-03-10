package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by BBPax on 06.03.17.
 */
public class Bomb extends AbstractGameObject implements Temporary {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private long lifeTime;
    private boolean isDead;
    private int power;

    public Bomb(int id, Point position, int power) {
        super(id, position.getX(), position.getY());
        this.lifeTime = 3000;
        this.isDead = false;
        this.power = power;
        log.info("Bomb(id = {}) was created in ( {} ; {} ) with lifeTime: {} and power {}", id,
                position.getX(), position.getY(), this.lifeTime, this.power);
    }

    @Override
    public void tick(long elapsed) {
        lifeTime -= elapsed;
        if (lifeTime <= 0L) {
            isDead = true;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return this.lifeTime;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }
}
