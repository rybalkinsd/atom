package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.atom.WorkWithProperties.getProperties;

/**
 * Created by BBPax on 06.03.17.
 */
public class Bomb extends AbstractGameObject implements Temporary {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    public static final long BOMB_LIFETIME = Long.valueOf(getProperties().getProperty("BOMB_LIFETIME"));

    @JsonIgnore
    private long lifeTime;
    @JsonIgnore
    private boolean isDead;
    @JsonIgnore
    private int power;
    @JsonIgnore
    private int pawnId;


    public Bomb(int id, Point position, int power, int pawnId) {
        super(id, position.getX(), position.getY());
        type = "Bomb";
        this.lifeTime = BOMB_LIFETIME;
        this.isDead = false;
        this.power = power;
        this.pawnId = pawnId;
        log.info("Bomb(id = {}) was created in ( {} ; {} ) with lifeTime: {} and power {}", id,
                position.getX(), position.getY(), this.lifeTime, this.power);
    }

    @Override
    public void setId(int newId) {
        super.setId(newId);
    }

    public int getPawnId() {
        return pawnId;
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

    public int getPower() {
        return power;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    public void setDead() {
        isDead = true;
    }
}
