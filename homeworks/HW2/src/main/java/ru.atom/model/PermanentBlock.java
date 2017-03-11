package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ikozin on 10.03.17.
 */
public class PermanentBlock implements Positionable {

    private Point position;
    private final int id;

    public PermanentBlock(Point position) {
        id = GameSession.getGameObjectId();
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
