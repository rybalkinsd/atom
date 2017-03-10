package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class Brick extends PositionableGameObject {

    public Brick(Point position) {
        super(position);
    }

    public Brick(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Brick{"
                + "id=" + getId()
                + " position=(" + position.getX() + ";" + position.getY()
                + '}';
    }
}
