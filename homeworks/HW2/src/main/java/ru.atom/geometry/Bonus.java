package ru.atom.geometry;

import ru.atom.model.Positionable;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;

public class Bonus implements GameObject, Positionable {
    private final Point position;
    private final int id;

    public Bonus (int x, int y) {
        this.position = new Point (x, y);
        id  = GameSession.getNextId();
    }

    @Override
    public Point getPosition () {
        return position;
    }

    @Override
    public int getId () {
        return id;
    }
}