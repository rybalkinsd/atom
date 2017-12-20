package ru.atom.gameserver.geometry;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Point implements Collider {

    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return ((Bar)other).isIncluding(this);
        } else /*if (other instanceof Point)*/ {
            return equals(other);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }
}
