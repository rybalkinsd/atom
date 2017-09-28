package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    public int x, y;
    // and methods
    public Point(int x, int y) {
    this.x = x;
    this.y = y;
    }

    public int x () { return x; }
    public int y () { return y; }

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            if (x == ((Point) other).x && y == ((Point) other).y) return true;

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
        if (x == point.x && y == point.y) return true;
        else return false;
        // your code here

    }
}
