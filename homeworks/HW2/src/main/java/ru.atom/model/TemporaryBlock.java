package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ikozin on 10.03.17.
 */
public class TemporaryBlock implements Positionable, Tickable {

    private final int id;
    private Point position;
    private long timeStep;

    public TemporaryBlock(Point position) {
        id = GameSession.getGameObjectId();
        this.position = position;
        timeStep = 0;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        timeStep = elapsed;
    }
}
