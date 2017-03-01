package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */implements Collider {
    // fields
    int x;
    int y;

    public Point(int x, int y) {
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
        if (this.x == (point).x && this.y == (point).y) {
            return true;
        }
        return false;


        // your code here
        //throw new NotImplementedException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.x == ((Point) other).x && this.y == ((Point) other).y) {
            return true;
        }
        return false;
    }
}
