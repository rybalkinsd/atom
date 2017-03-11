package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean isHigher(Point p) {
        return this.y >= p.y;
    }

    boolean isLower(Point p) {
        return this.y <= p.y;
    }

    boolean isRight(Point p) {
        return this.x >= p.x;
    }

    boolean isLeft(Point p) {
        return this.x <= p.x;
    }

    @Override
    public boolean isColliding(Collider other) {
        return this.equals(other);
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return this.x == point.x && this.y == point.y;
    }
}
