package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    private int x;
    private int y;
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
        if ((x == point.x) && (y == point.y))
                return true;
        else return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        if (other instanceof  Point) {
            // cast from Object to Point
            Point point = (Point) other;

            if ((x == point.x) && (y == point.y))
                return true;
            else return false;
        }
        if (other instanceof Bar) {
            //cast from Object to Bar
            Bar bar = (Bar) other;
            int x1 = bar.getX1();
            int x2 = bar.getX2();
            int y1 = bar.getY1();
            int y2 = bar.getY2();

            if ((x > x1) || (x < x2) || (y > y1) || (y < y2))
                return false;
            else
                return true;
        }
        return false;
    }

    public Point(int a, int b) {
        x = a;
        y = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
