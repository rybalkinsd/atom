package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    protected int x;
    protected int y;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        if (this.x == point.x && this.y == point.y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider otherPoint) {
        if (this == otherPoint) return true;
        if (otherPoint == null || getClass() != otherPoint.getClass()) return false;

        return this.equals(otherPoint);
    }
}
