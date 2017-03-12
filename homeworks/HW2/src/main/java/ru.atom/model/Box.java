package ru.atom.model;

import ru.atom.geometry.Point;

public class Box implements Positionable {
    public int id;
    public Point position;

    public Box(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
