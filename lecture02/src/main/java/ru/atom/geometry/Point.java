package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private final int x;
    private final int y;

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

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
        if (this.x == point.getX() && this.y == point.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isColliding(Collider o) {
        if (o instanceof Point) {
            return this.equals(o);
        } else if (o instanceof Bar) {
            return o.isColliding(this);
        }
        return false;
    }
}
