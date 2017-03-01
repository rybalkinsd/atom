package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */ implements Collider{

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {

        return x;
    }

    @Override
    public boolean isColliding(Collider other) {
        Point point = (Point) other;
        if(this.equals(point)) {
            return true;
        } else {
            return false;
        }
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

        if(x == point.getX() && y == point.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
