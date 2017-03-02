package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Point point = (Point) o;
        if (this.getX() == point.getX() && this.getY() == point.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.getX() == ((Point) other).getX()
            && this.getY() == ((Point) other).getY()) {
            return true;
        }

        return false;
    }
}
