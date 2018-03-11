package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

        return (point.x == this.x && point.y == this.y);
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return equals(other);
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return (bar.getLeftDownPoint().getX() <= x
                    && bar.getLeftDownPoint().getY() <= y
                    && bar.getRigthUpPoint().getX() >= x
                    && bar.getRigthUpPoint().getY() >= y);
        }
        return false;
    }
}
