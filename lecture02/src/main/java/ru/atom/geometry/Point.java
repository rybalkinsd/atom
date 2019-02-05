package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;
    // fields
    // and methods

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

        if (point.getX() == this.x
                && point.getY() == this.y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            if ((point.x == this.x) && (point.y == this.y)) {
                return true;
            } else {
                return false;
            }
        }
        if (other instanceof Bar) {
            return other.isColliding(this);
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
