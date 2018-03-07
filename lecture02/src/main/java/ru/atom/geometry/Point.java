package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider
{
    private int x;
    private int y;

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
    public Point() {
        x = y = 0;
    }
    public Point (int x, int y) {
        this.x = x;
        this.y = y;
    }
    // fields
    // and methods

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
        return x == point.getX() && y == point.getY();
    }
    @Override
     public boolean isColliding(Collider other) {
        return equals(other);
    }
}
