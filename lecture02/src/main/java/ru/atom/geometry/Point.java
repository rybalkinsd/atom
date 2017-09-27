package ru.atom.geometry;

public class Point implements Collider {

    private int x = 0;
    private int y = 0;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean moreOrEquals(Point o) {
        return this.x >= o.getX() && this.y >= o.getY();
    }

    public boolean lessOrEquals(Point o) {
        return this.x <= o.getX() && this.y <= o.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point other = (Point) o;
        return x == other.x && y == other.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        Point o = (Point) other;
        return this.x == o.x && this.y == o.y;
    }
}
