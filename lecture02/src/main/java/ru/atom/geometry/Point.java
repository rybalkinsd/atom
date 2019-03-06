package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {

    // fields
    // and methods
    private int x;
    private int y;

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


    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point)
            return equals((Point) other);

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return ((x - bar.getLeft()) * (x - bar.getRight()) <= 0) &&
                    ((y - bar.getBottom()) * (y - bar.getTop()) <= 0);
        }

        return false;
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
        return (this.x == point.getX() && this.y == point.getY());
    }
}
