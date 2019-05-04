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
     * @param obj - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) obj;

        return this.x == point.getX()
                && this.y == point.getY();
        // your code here
    }

    @Override
    public boolean isColliding(Collider other) {
        return this.equals(other);
    }
}
