package ru.atom.model.actor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.util.V;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Actor implements Tickable {
    private final static Logger log = LogManager.getLogger(Actor.class);
    private V position;
    private V velocity;

    @Override
    public void tick(long time) {
        move(time);
    }

    private void move(long time) {
        V before = position;
        position = position.move(velocity.times(time));
        log.info("Moved: {} -> {}.", before, position);
    }

    public void destroy() {
    }

    public V getPosition() {
        return position;
    }

    public Actor setPosition(V position) {
        this.position = position;
        return this;
    }

    public Actor setVelocity(V velocity) {
        this.velocity = velocity;
        return this;
    }

    @JsonProperty("type")
    private String getClassName() {
        return getClass().getSimpleName();
    }
}
