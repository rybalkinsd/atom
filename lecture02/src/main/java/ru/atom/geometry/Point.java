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

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
