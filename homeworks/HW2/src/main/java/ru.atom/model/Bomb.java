package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by home on 10.03.2017.
 */
public class Bomb extends AbstractGameObject implements Temporary {

    private static final Logger log = LogManager.getLogger(Bomb.class);

    private long lifeTimeBomb;
    private long boomTimeBomb;

    public Bomb(Point position) {
        super(position.getX(), position.getY());
        this.lifeTimeBomb = 3000;
        this.boomTimeBomb = 0L;
        log.info("The Bomd has been planted id={} x={} y={}", getId(), position.getX(), position.getY());
    }

    @Override
    public void tick(long elapsed) {
        boomTimeBomb += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeBomb;
    }

    @Override
    public boolean isDead() {
        boolean boom = (boomTimeBomb > lifeTimeBomb);
        log.info("The Bomb was blown up!");
        return boom;
    }
}
