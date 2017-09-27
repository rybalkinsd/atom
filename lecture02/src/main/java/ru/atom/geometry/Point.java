package ru.atom.geometry;

public class Point implements Collider /* super class and interfaces here if necessary */ {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isColliding(Collider c) {
        if (this.getClass().equals(c.getClass())) {
            Point point = (Point) c;
            if (this.getX() == point.getX() && this.getY() == point.getY())
                return true;
        } else {
            Bar bar = (Bar) c;
            Point point = this;
            return (bar.isColliding(point));
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if (this.getX() == point.getX() && this.getY() == point.getY())
            return true;
        return false;
    }
}
