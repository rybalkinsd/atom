package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Any entity of game mechanics
 */
public abstract class GameObject {
    protected final GameSession session;
    protected final int id;
    protected Point position;

    public GameObject(GameSession session, Point position) {
        this.position = position;
        this.id = session.getNewId();
        this.session = session;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }
}
