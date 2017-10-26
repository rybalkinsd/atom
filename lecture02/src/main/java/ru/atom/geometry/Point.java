package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    // fields
    // and methods
    int x;
    int y;

    Point(int x1, int y1) {
        x = x1;
        y = y1;
    }
    /**
     * @ param o- other object to check equality with
     * @return true if two points are equal and not null.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        if (this.x == point.x && this.y == point.y) return true;

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        Point point = (Point) other;
        if (point.x == this.x && point.y == this.y) return true;
        return false;
    }
}