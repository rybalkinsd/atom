package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    // and methods
    private final int x;
    private final int y;

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

        if (point.getX() == this.getX() && point.getY() == this.getY())
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        return this.equals(other);
    }
}
