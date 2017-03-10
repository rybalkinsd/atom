package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by home on 10.03.2017.
 */
public class Bonus extends AbstractGameObject implements Temporary {
    private static final Logger log = LogManager.getLogger(Bonus.class);

    private long lifeTimeBonus;
    private long passTimeBonus;

    public enum TypeBonus {
        SPEED,
        PLUSONEBOMD,
        FIRE
    }

    TypeBonus newType;

    public Bonus(Point position, TypeBonus newType) {
        super(position.getX(), position.getY());
        this.newType = newType;
        this.lifeTimeBonus = 5000;
        this.passTimeBonus = 0L;
        log.info("On the map has appeared Bonus id={}, x={}, y={}", getId(), position.getX(), position.getY());

    }

    @Override
    public void tick(long elapsed) {
        passTimeBonus += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return lifeTimeBonus;
    }

    @Override
    public boolean isDead() {
        boolean bonus = (passTimeBonus > lifeTimeBonus);
        log.info("The Bonus was disappeared!");
        return bonus;
    }
}
