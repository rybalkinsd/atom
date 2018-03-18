package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    // and methods
    private int x;
    private int y;

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    public Point(int x_, int y_) { x = x_; y = y_; }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == getClass()) {
            return this.equals(other);
        }
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return point.x == x && point.y == y;
    }
}
