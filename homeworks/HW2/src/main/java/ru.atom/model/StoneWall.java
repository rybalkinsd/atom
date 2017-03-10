package ru.atom.model;

import ru.atom.geometry.Point;

public class StoneWall implements Positionable {

    private int sWid;
    private  Point position;

    public StoneWall(Point pos) {
        this.position = pos;
        this.sWid = GameSession.getId();
    }

    @Override
    public int getId() {
        return this.sWid;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
