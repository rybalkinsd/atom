package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    int x;
    int y;

    Point () {
    }

    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }
    // and methods


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
        return isColliding(point);
        //throw new NotImplementedException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point point = (Point) other;
        if (x != point.x || y != point.y)
            return false;
        return true;
    }
}
