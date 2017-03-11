package ru.atom.model;

import ru.atom.geometry.Point;

public class PositionableObject extends GameObjectImpl implements Positionable {

    private Point position;

    @Override
    public String toString() {
        String line = "Class: " + this.getClass().getSimpleName() + " id: " + this.getId();
        return line;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point pos) {
        position = pos;
    }

}
