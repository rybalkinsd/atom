package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private int x;
    private int y;

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

        // your code here
        return ((x == point.getX()) && (y == point.getY()));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            return this.equals(other);
        }
        if (other.getClass() == Bar.class) {
            Bar bar = (Bar) other;
            return (bar.getLeftCornerPoint().getX() <= x
                    && bar.getLeftCornerPoint().getY() <= y
                    && bar.getRightCornerPoint().getX() >= x
                    && bar.getRightCornerPoint().getY() >= y);
        }
        throw new IllegalArgumentException();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
