package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider  /* super class and interfaces here if necessary */ {
    // fields
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    // and methods

    // constructors

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
        if (this.getX() == point.getX() && this.getY() == point.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        if (getClass() == other.getClass()) {
            Point point = (Point) other;
            return point.equals(this);
        }

        return false;
    }
}
