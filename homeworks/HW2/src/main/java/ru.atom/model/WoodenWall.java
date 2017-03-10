package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 10.03.17.
 */
public class WoodenWall implements  Positionable {
    private int wwid;
    private  Point position;

    public WoodenWall(Point pos) {
        this.position = pos;
        this.wwid = GameSession.getId();
    }

    @Override
    public int getId() {
        return this.wwid;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
