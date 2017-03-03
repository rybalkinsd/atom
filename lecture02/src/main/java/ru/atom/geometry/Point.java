package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    int xCoor;
    int yCoor;

    // and methods

    public boolean isColliding(Collider other) {
        return (this.equals(other)) && (this.equals(other));
    }

    public Point (int x, int y) {
        this.xCoor = x;
        this.yCoor = y;
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
        return (this.xCoor == point.xCoor) && (this.yCoor == point.yCoor);
    }
}
