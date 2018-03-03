package ru.atom.geometry;


/* super class and interfaces here if necessary */
public class Point implements Collider {
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
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Point
        Point point = (Point) o;

        return x == point.getX() && y == point.getY();
    }

    /**
     * colliding with all type geometric figure
     * can be implemented in this class
     */
    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return equals(point);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point firstCornerBar = bar.getFirstCorner();
            Point secondCornerBar = bar.getSecondCorner();
            return firstCornerBar.getX() <= x && x <= secondCornerBar.getX()
                   && firstCornerBar.getY() <= y && y <= secondCornerBar.getY();
        }
        return false;
    }
}
