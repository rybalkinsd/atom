package ru.atom.model;

import ru.atom.geometry.Point;

public class Tile implements Positionable {
    private final int id;

    // Entity position on map grid
    private final Point position;

    private final Materials material;

    public enum Materials {
        GRASS, WALL, WOOD
    }

    public Tile(Point position, Materials material) {
        this.id = GameSession.getNextId();
        this.position = position;
        this.material = material;
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
