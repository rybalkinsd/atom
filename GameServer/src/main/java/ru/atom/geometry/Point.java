package ru.atom.geometry;

//TODO insert your implementation of geometry here
public class Point implements GeomObject {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = (int)(31 * result + y);
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        return false;
    }

    @Override
    public Point getPosition() {
        return this;
    }

    @Override
    public String toString() {
        return "\"position\":{\"x\":" + (int)x + ",\"y\":" + (int)y + "}";
    }

}
