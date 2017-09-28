package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    // and methods

    private int x;
    private int y;

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

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return ((Bar)other).hasInto(this);
        } else /*if (other instanceof Point)*/ {
            return equals(other);
        }
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
}
