package ru.atom.geometry;

import ru.atom.model.Positionable;

public class Rectangle implements Positionable {

    private  int x1;
    private  int x2;
    private  int y1;
    private  int y2;
    private int x;
    private int y;

    public Rectangle(int x1, int x2, int y1, int y2) {
        x = (x1 + x2) / 2;
        y = (y1 + y2) / 2;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }


    @Override
    public Rectangle getSpace() {
        return this;
    }

    @Override
    public Point getPosition() {
        return new Point(x,y);
    }
}



