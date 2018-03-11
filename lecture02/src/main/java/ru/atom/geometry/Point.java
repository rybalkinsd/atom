package ru.atom.geometry;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */ implements Collider {
    private int x;
    private int y;
    // and methods

    public Point() {
        x = y = 0;
    }

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
        if (o == null || !(o instanceof Point)) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return x == point.getX() && y == point.getY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return ((Bar) other).isColliding(this);
        }
        if (other instanceof Point) {
            return equals(other);
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
