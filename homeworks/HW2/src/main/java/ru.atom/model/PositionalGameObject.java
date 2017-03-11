package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class PositionalGameObject implements Positionable {
    private static int maxId = 0;
    private final int id;
    protected Point position;

    public PositionalGameObject(int x, int y) {
        this(new Point(x, y));
    }

    public PositionalGameObject(Point position) {
        id = maxId;
        maxId++;
        this.position = position;
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
