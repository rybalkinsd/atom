package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 10.03.17.
 */
public class WoodenWall implements  Positionable {
    private int WWid;
    private  Point Position;
    public WoodenWall(Point pos) {
        this.Position = pos;
        this.WWid = GameSession.getId();
    }
    @Override
    public int getId() {
        return this.WWid;
    }

    @Override
    public Point getPosition() {
        return this.Position;
    }
}
