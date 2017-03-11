package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Antonio on 11.03.2017.
 */
public class Explodingstone implements Positionable {
    private Point position;
    private int id;
    private boolean isDead = false;

    public Explodingstone(int x, int y) {
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
