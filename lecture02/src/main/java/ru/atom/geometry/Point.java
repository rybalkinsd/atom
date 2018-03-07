package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private final int x;
    private final int y;

    public int get_X() {
        return x;
    }

    public int get_Y() {
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
        return x == point.get_X() && y == point.get_Y();
    }

    @Override
    public boolean isColliding(Collider other) {
        boolean flag = false;
        if (other instanceof Point) {
            Point point = (Point) other;
            flag = equals(point);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point firstBar = bar.get_First();
            Point secondBar = bar.get_Second();
            flag = firstBar.get_X() <= x && x <= secondBar.get_X()
                && firstBar.get_Y() <= y && y <= secondBar.get_Y();
        }
        return flag;
    }
}