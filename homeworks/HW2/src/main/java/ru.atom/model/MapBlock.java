package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class MapBlock implements Positionable {

    private Point pointOfMapBlock;
    private int id;

    public MapBlock() {
        pointOfMapBlock = new Point(0,0);
        id = 0;
    }

    @Override
    public Point getPosition() {
        return pointOfMapBlock;
    }

    @Override
    public int getId() {
        return id;
    }
}
