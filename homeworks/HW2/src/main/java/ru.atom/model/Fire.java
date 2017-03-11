package ru.atom.model;

import ru.atom.geometry.Point;

public class Fire implements Positionable {
    private final int id;

    // Entity position on map grid
    private Point position;

    private Bomb bomb;

    public Fire(Point position, Bomb bomb) {
        this.id = GameSession.getNextId();
        this.position = position;
        this.bomb = bomb;
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
