package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;


    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point)
            return this.equals((Point)other);
        else if (other instanceof Bar)
            return other.isColliding(this);
        else
            return false;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        return getX() == point.getX() && getY() == point.getY();
    }
}
