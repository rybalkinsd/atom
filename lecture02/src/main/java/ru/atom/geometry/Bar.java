package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Bar implements Collider {
    private int firstPointX;
    private int firstPointY;
    private int secondPointX;
    private int secondPointY;

    public Bar(int x1, int y1, int x2, int y2) {
        this.firstPointX = Math.min(x1, x2);
        this.firstPointY = Math.min(y1, y2);
        this.secondPointX = Math.max(x1, x2);
        this.secondPointY = Math.max(y1, y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return (this.firstPointX == bar.firstPointX
                && this.firstPointY == bar.firstPointY
                && this.secondPointX == bar.secondPointX
                && this.secondPointY == bar.secondPointY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return ((this.firstPointX <= point.getX())
                    && (point.getX() <= this.secondPointX)
                    && (this.firstPointY <= point.getY())
                    && (point.getY() <= this.secondPointY));
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return !((this.secondPointX < bar.firstPointX) || (this.secondPointY < bar.firstPointY)
                   || (this.firstPointX > bar.secondPointX) || (this.firstPointY > bar.secondPointY));
        }
        return false;
    }
}
