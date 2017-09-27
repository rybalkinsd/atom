package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;

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

        return point.getX() == this.getX() && point.getY() == this.getY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return equals(other);
        } else if (other instanceof Bar) {
            return Utils.barIsCollidingWithPoint((Bar) other, this);
        }

        return false;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
