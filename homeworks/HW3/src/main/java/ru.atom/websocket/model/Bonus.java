package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by BBPax on 06.03.17.
 */
public class Bonus extends AbstractGameObject implements Temporary {
    private static final Logger log = LogManager.getLogger(Bonus.class);
    @JsonProperty
    private long lifeTime;
    @JsonProperty
    private boolean isDead;
    @JsonProperty
    private Type bonusType;

    public enum Type {
        SPEED,
        BOMB,
        RANGE
    }

    public Bonus(int id, Point position, Type bonusType) {
        super(id, position.getX(),position.getY());
        type = "Bonus";
        isDead = false;
        lifeTime = 5000;
        this.bonusType = bonusType;
        log.info("Bonus(id = {}) was created in ( {} ; {} ) with lifeTime: {} and bonus: {}",
                id, position.getX(), position.getY(), this.getLifetimeMillis(), this.bonusType);
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
        return lifeTime;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }
}
