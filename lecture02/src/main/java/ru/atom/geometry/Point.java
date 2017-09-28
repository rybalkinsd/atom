package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
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
        if ((this.x == point.x) && (this.y == point.y)) {
            return true;
        } else {
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override

    public boolean isColliding(Collider other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        // cast from Object to Point
        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            // your code here
            if (this.x >= bar.getfirsCornerX() && this.x <= bar.getsecondCornerX()
                    && this.y >= bar.getfirstCornerY() && this.y <= bar.getsecondCornerY()) {
                return true;
            } else {
                return false;
            }
        }
        if (other instanceof Point) {

            Point point = (Point) other;
            if (this.equals(other)) {
                return true;
            } else
                return false;
        }
        return false;
    }
}
