package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class AbstractGameObj implements Positionable {

    protected Point position;
    private int id;
    private static int nextId = 0;

    public AbstractGameObj(int x, int y) {
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
        return this.position;
    }

}