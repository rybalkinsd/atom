package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class Bonus extends TemporaryGameObject {

    public Bonus(Point position, long lifetimeMillis) {
        super(position, lifetimeMillis);
    }

    public Bonus(int x, int y, long lifetimeMillis) {
        super(x, y, lifetimeMillis);
    }

    @Override
    public String toString() {
        return "Bonus{"
                + "id=" + getId()
                + " position=(" + position.getX() + ";" + position.getY()
                + " lifetime=(" + getLifetimeMillis()
                + " isDead=(" + isDead()
                + '}';
    }
}
