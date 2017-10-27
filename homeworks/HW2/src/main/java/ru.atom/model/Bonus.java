package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class Bonus implements Positionable {

    private Point pointOfBonus;
    private int id;

    public Bonus() {
        pointOfBonus = new Point(0,0);
        id = 0;
    }

    @Override
    public Point getPosition() {
        return pointOfBonus;
    }

    @Override
    public int getId() {
        return id;
    }
}
