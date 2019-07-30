package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {

    private final int x;
    private final int y;

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

    /**
     * Calculate horizontal distance to other point
     * @param other point
     * @return distance in x direction to other point
     */
    public int getHorizontalDistanceTo(Point other) {
        return Math.abs(x - other.x);
    }

    /**
     * Calculate vertical distance to other point
     * @param other point
     * @return distance in y direction to other point
     */
    public int getVerticalDistanceTo(Point other) {
        return Math.abs(y - other.y);
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
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return bar.isColliding(this);
        }
        return false;
    }
}
