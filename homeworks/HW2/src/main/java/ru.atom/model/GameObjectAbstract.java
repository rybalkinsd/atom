package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class GameObjectAbstract implements Positionable {
    protected Point position;
    protected final int id;
    protected static int nextId = 1;
    protected boolean isActive = true;

    public GameObjectAbstract(int x, int y) {
        position = new Point(x, y);
        id = nextId++;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
