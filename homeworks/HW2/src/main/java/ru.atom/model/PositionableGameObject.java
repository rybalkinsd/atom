package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public abstract class PositionableGameObject implements Positionable {

    protected int id;
    protected Point position;

    public PositionableGameObject(Point position) {
        id = UniqueId.getId();
        this.position = position;
    }

    public PositionableGameObject(int x, int y) {
        id = UniqueId.getId();
        position = new Point(x, y);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
