package ru.atom.model;

import ru.atom.geometry.Point;

public class Bonus implements Positionable {
    private final int id;

    // Entity position on map grid
    private final Point position;
    private final Types type;

    public enum Types {
        SPEED, BOMB, FIRE
    }

    public Bonus(Point position, Types type) {
        this.id = GameSession.getNextId();
        this.position = position;
        this.type = type;
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
