package ru.atom.model.actor;

import ru.atom.util.V;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Actor implements Tickable {
    private V position;
    private V velocity;

    @Override
    public void tick(long time) {
        move(time);
    }

    protected void move(long time) {
        position = position.move(velocity.times(time));
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
}
