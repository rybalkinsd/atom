package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
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
        Point point = (Point) o;

        if (this.x == point.x) {
            if (this.y == point.y) {
                return true;
            } else return false;
        } else return false;
    }
    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        else return false;
    }
}
