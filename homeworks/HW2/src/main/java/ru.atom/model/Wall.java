package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Western-Co on 08.03.2017.
 */
public class Wall implements Positionable {
    private int id;
    private Point position;

    public Wall(Point position) {
        this.id = GameSession.setObjectId();
        this.position = position;
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
