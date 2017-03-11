package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb extends AbstractGameObject implements Temporary {

    private static final Logger log = LogManager.getLogger(Bomb.class);

    private long lifeTimeBomb;
    private long boomTime;

    public Bomb(Point position) {
        super(position.getX(), position.getY());
        this.lifeTimeBomb = 3000;
        this.boomTime = 0L;
        log.info("Bomb has been planted! id={} x={} y={}", getId(), position.getX(), position.getY());
    }

    @Override
    public void tick(long elapsed) {
        boomTime += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeBomb;
    }

    @Override
    public boolean isDead() {
        boolean boom = (boomTime > lifeTimeBomb);
        log.info("CABOOM!!");
        return boom;
    }
}