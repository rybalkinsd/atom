package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;

/**
 * Created by Alexandr on 05.12.2017.
 */
public abstract class AbstractGameObject implements GameObject {

    private final int id;
    private final Point position;

    public AbstractGameObject(int id, Point position) {
        this.id = id;
        this.position = new Point(position);
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
