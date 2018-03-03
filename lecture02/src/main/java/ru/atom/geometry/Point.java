package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    public int x;
    public int y;

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
            return ((x >= Math.min(bar.x1, bar.x2)) && (x <= Math.max(bar.x1, bar.x2))
                    && (y >= Math.min(bar.y1, bar.y2)) && (y <= Math.max(bar.y1, bar.y2)));
        }
    }

    public Point(int xx, int yy) {
        this.x = xx;
        this.y = yy;
    }
}
