package ru.atom.model.object.actor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.object.GameObject;
import ru.atom.util.V;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Actor extends GameObject implements Tickable {
    private final static Logger log = LogManager.getLogger(Actor.class);
    private V velocity = V.ZERO;

    @Override
    public void tick(long time) {
        V before = position;
        move(time);
//        log.info("Moved: {} -> {}.", before, position);
    }

    private void move(long time) {
        position = position.move(velocity.times(time));
        velocity = V.ZERO;
    }

    public Actor setVelocity(V velocity) {
        this.velocity = velocity;
        return this;
    }
}
