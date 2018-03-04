package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider  /* super class and interfaces here if necessary */ {
    // fields
    // and methods

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    private int x;
    private int y;

    public Point(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isColliding(Collider other) {

        return other instanceof Collider && this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return x == point.x && y == point.y;
    }
}
