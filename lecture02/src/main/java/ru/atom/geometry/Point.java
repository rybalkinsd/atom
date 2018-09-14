package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private int x;
    private int y;
    // fields
    // and methods

    /**
     * @param  - other object to check equality with
     * @return true if two points are equal and not null.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // Check if points merge
        if (this.x == point.getX() && this.y == point.getY()) {
            return true;
        }   else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        //Check if collide
        return this.equals(other);
    }
}
