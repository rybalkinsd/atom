package ru.atom.model;

import ru.atom.geometry.Point;

public class Box implements Positionable {
    private final int id;
    private final Point position;

    public Box(Point position) {
        this.id = GameSession.setGameObjectId();
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }
}