package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */

public class Point implements Collider {
    private int x;
    private int y;

    public Point() {
        setCoordinates(0, 0);
    }

    public Point(int x, int y) {
        setCoordinates(x, y);
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        return (this.x == point.x && this.y == point.y);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point)
            return (this.equals(other));
        else if (other instanceof Bar)
            return other.isColliding(this);
        throw new NotImplementedException();
    }
}
