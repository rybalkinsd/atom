package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class AbstractGameObject implements Positionable {

    private int id;
    private static int nextId = 0;
    protected Point position;

    protected AbstractGameObject(int x, int y) {
        this.position = new Point(x, y);
        this.id = nextId;
        nextId++;
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
