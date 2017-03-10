package ru.atom.model;

import ru.atom.geometry.Point;


public class StoneColumns implements Positionable {

    private Point position;
    private final int id;

    public StoneColumns(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();
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
