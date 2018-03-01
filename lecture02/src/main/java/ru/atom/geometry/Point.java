package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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
        return x == point.x && y == point.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        } else if (other instanceof Bar) {
            return ((Bar) other).containsPoint(x, y);
        } else {
            return false;
        }
    }
}
