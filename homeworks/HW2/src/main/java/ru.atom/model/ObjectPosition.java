package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class ObjectPosition extends GameObjectId implements Positionable {

    protected Point position;

    public void setPosition(Point position1) {
        position = position1;
    }

    @Override
    public Point getPosition() {
        return new Point(position.getX(), position.getY());
    }
}
