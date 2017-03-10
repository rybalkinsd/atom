package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class Bonus extends TemporaryGameObject {

    public Bonus(Point position, long LifetimeMillis) {
        super(position, LifetimeMillis);
    }

    public Bonus(int x, int y, long LifetimeMillis) {
        super(x, y, LifetimeMillis);
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "id=" + getId() +
                " position=(" + position.getX() + ";" + position.getY() +
                " lifetime=(" + getLifetimeMillis() +
                " isDead=(" + isDead() +
                '}';
    }
}
