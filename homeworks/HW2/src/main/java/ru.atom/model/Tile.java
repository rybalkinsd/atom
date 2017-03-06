package ru.atom.model;

import ru.atom.geometry.Point;

public class Tile implements Positionable {
    private Point position;
    private Material material = Material.GRASS;
    private final int id;

    public enum Material {
        GRASS, WOOD, WALL;
    }

    public Tile(int x, int y, Material material) {
        this.position = new Point(x, y);
        this.material = material;
        this.id = GameSession.nextId();
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return id;
    }
}
