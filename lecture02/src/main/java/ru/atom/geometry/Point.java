package ru.atom.geometry;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */implements Collider {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
        if (this.x == (point).x && this.y == (point).y) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.x == ((Point) other).x && this.y == ((Point) other).y) {
            return true;
        }
        return false;
    }
}
