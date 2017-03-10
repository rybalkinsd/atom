package ru.atom.model;

import ru.atom.geometry.Point;

public class StoneWall implements Positionable {

    private int SWid;
    private  Point Position;
    public StoneWall(Point pos) {
        this.Position = pos;
        this.SWid = GameSession.getId();
    }
    @Override
    public int getId() {
        return this.SWid;
    }

    @Override
    public Point getPosition() {
        return this.Position;
    }
}
