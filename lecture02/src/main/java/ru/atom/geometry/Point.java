package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    int x;
    int y;
    // fields
    // and methods

    Point(int x, int y) {
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
        if ((this.x == point.x) && (this.y == point.y)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) {
            return true;
        } else {
            return false;
        }
    }
}
