package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private float X;
    private float Y;
    // and methods
    Point(int x, int y) {
        X = x;
        Y = y;
    }

    public float Get_X() {
        return X;
    }
    public float Get_Y() {
        return Y;
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
        return this.X==point.X && this.Y==point.Y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass())
            return this.equals(other);
        else {
            return other.isColliding(this);
        }
    }
}
