package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;

import static ru.atom.WorkWithProperties.getProperties;

/**
 * Created by BBPax on 11.05.17.
 */
public class Fire extends AbstractGameObject implements Temporary  {
    private static final Logger log = LogManager.getLogger(Fire.class);
    public static final long FIRE_LIFETIME = Long.valueOf(getProperties().getProperty("FIRE_LIFETIME"));

    @JsonIgnore
    private long lifeTime;
    @JsonIgnore
    private boolean isDead;

    public Fire(int id, Point position) {
        super(id, position.getX(), position.getY());
        type = "Fire";
        this.lifeTime = FIRE_LIFETIME;
        this.isDead = false;
        bar = new Bar(new Point(position.getX() + CENTERED_BAR_SHIFT, position.getY() + CENTERED_BAR_SHIFT),
                CENTERED_BAR_SIZE);
        log.info("Fire(id = {}) was created in ( {} ; {} ) with lifeTime: {} and bar {}", id,
                position.getX(), position.getY(), this.lifeTime, bar.toString());
    }

    /**
     * Applies changes to game objects that happen after elapsed time
     *
     * @param elapsed time that expired in this tick
     */
    @Override
    public void tick(long elapsed) {
        lifeTime -= elapsed;
        if (lifeTime <= 0L) {
            isDead = true;
        }
    }

    /**
     * @return lifetime in milliseconds
     */
    @Override
    public long getLifetimeMillis() {
        return this.lifeTime;
    }

    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     *
     * @return true if GameObject is dead
     */
    @Override
    public boolean isDead() {
        return isDead;
    }
}
