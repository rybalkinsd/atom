package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by BBPax on 06.03.17.
 */
public class Bonus extends AbstractGameObject implements Temporary {
    private static final Logger log = LogManager.getLogger(Bonus.class);
    @JsonIgnore
    private long lifeTime;
    @JsonIgnore
    private boolean isDead;

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
        lifeTime = 10000;
        this.bonusType = bonusType;
        bar = new Bar(new Point(32 * position.getX(), 32 * position.getY()), 30);
        log.info("Bonus(id = {}) was created in ( {} ; {} ) with lifeTime: {} and bonus: {} with bar {}",
                id, position.getX(), position.getY(), this.getLifetimeMillis(), this.bonusType, bar.toString());
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

    public Type getBonusType() {
        return bonusType;
    }

    public void setDead() {
        isDead = true;
    }
}
