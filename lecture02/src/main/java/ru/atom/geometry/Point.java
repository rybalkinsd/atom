package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    private int x;
    private int y;

    Point() {
        this.setX(0);
        this.setY(0);
    }
    Point(int x, int y) {
        this.setX(x);
        this.setY(y);
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
        return (point.getX() == this.getX()) && (point.getY() == this.getY());
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

    @Override
    public boolean isColliding(Collider other) {
        if(other instanceof Point) {
            Point point = (Point) other;
            return this.equals(other);
        } else return false;
    }
}
