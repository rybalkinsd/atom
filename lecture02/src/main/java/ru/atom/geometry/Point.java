package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
class Point implements Collider {
    private final int x;
    private final int y;

    Point(int x, int y) {
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
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Point) {
            Point otherPoint = (Point) other;
            return this.equals(otherPoint);
        } else {
            Bar otherBar = (Bar) other;
            return otherBar.isColliding(this);
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
