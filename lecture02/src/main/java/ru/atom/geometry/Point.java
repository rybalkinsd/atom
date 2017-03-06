package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    public int x;
    public int y;// fields

    // and methods
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
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() == this.getClass()) {
            Point point = (Point) o;
            if (point.x == this.x && point.y == this.y)
                return true;
        }
        return false;
        // cast from Object to Point

        // your code here

    }

    public boolean isColliding(Collider other) {
        if (equals(other)) return true;
        return false;
    }
}