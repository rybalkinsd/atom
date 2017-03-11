package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    // fields
    private int x;
    private int y;

    // and methods
    public Point(int x, int y) {
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
        if (this.x == (point).x && this.y == (point).y) {
            return true;
        }
        return false;
    }

    // your code here
    @Override
    public boolean isColliding(Collider otherP) {
        return this.equals(otherP);
    }
}
