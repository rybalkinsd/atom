package gs.model;

import gs.geometry.Point;

public abstract class Field implements Positionable {
    private int id;
    private Point point;
    private static int nextId = 0;

    public Field(int x, int y) {
        this.point = new Point(x, y);
        this.id = nextId;
        nextId++;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return this.point;
    }
}
