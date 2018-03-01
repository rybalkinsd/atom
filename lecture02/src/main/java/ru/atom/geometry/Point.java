package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return (this.x == point.x && this.y == point.y);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return equals(other);
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return (bar.isColliding(this));
        }
        return false;
    }
}