package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
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
        return x == point.x && y == point.y;
        // your code here
    }

    @Override
    public boolean isColliding(Collider other) {
        return this.equals(other);
    }

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
