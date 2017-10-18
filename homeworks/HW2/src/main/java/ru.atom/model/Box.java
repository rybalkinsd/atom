package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rect;

public class Box implements Positionable, Tickable {
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;

    Point position;
    Rect rec;

    public Box(Point pos) {
        this.position = pos;

    }

    public void setRect() {
        Point v1 = new Point(position.getX() - WIDTH / 2, position.getY() - HEIGHT / 2);
        Point v2 = new Point(position.getX() + WIDTH / 2, position.getY() + HEIGHT / 2);
        this.rec = new Rect(v1, v2);
    }

    public Point getPosition() {
        return position;
    }

    public Rect getRec() {
        return  rec;
    }

    public void tick(long elapsed) {
        //допишу потом
    }

    public int getId() {
        return 100;
    }
}