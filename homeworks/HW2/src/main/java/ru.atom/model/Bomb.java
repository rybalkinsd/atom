package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bomb extends AbstractGameObject implements Temporary {
    private static final Logger logger = LogManager.getLogger(Bomb.class);

    private long lifeTimeMillis;
    private long passedTimeMillis;

    public Bomb(int x, int y, long lifeTimeMillis) {
        super(x, y);
        this.lifeTimeMillis = lifeTimeMillis;
        logger.info("new Bomb! id = {} x = {} y = {}", getId(), x, y);

    }

    @Override
    public void tick(long elapsed) {
        passedTimeMillis += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeMillis;
    }

    @Override
    public boolean isDead() {
        boolean isDead = (passedTimeMillis > lifeTimeMillis);
        logger.info("BOOM! (BombId = {})", getId());
        return isDead;
    }


}
