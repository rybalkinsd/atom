package ru.atom.model;


import ru.atom.geometry.Point;

public class Brick implements Positionable {

    private final int id;
    private final Point position;

    public Brick(int id, Point position) {
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
