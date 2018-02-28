package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    private int x;
    private int y;
    // and methods


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int  y) {
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
        if ((this.x == point.x) && (this.y == point.y))
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            if ((this.x == point.x) && (this.y == point.y))
                return true;
            else
                return false;
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            int maxY = bar.getMaxY();
            int maxX = bar.getMaxX();
            int minY = bar.getMinY();
            int minX = bar.getMinX();
            if ((maxY < y) || (minY > y)
                    || (maxX < x) || (minX > x))
                return false;
            else
                return true;
        }
        return false;
    }
}
