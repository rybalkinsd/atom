package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by gammaker on 05.03.2017.
 */
public abstract class AbstractGameObject implements Positionable {
    public final int id;
    protected Point pos;

    public AbstractGameObject(int x, int y) {
        pos = new Point(x, y);
        id = GameSession.getUniqueId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return pos;
    }
}