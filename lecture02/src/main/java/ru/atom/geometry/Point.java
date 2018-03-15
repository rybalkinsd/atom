package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

        return ((point.x == this.x) && (point.y == this.y));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return ((point.x == this.x) && (point.y == this.y));
        } else {
            Bar bar = (Bar) other;
            return ((x >= bar.getLeft().getX()) && (x <= bar.getRight().getX())
                    && (y >= bar.getLeft().getY()) && (y <= bar.getRight().getY()));
        }
    }

    public Point(int xx, int yy) {
        this.x = xx;
        this.y = yy;
    }
}
