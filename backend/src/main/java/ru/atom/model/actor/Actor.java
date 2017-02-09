package ru.atom.model.actor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.util.V;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Actor extends GameObject implements Tickable {
    private final static Logger log = LogManager.getLogger(Actor.class);
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

    public Actor setVelocity(V velocity) {
        this.velocity = velocity;
        return this;
    }
}
