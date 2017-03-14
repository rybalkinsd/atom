package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    // and methods

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Coordinates
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @param o
     *            - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Point
        Point point = (Point) o;

        return (x == point.x && y == point.y);
    }

    @Override
    public boolean isColliding(Collider other) {

        if (other instanceof Point)
            return equals(other);

        throw new NotImplementedException();
    }
}
