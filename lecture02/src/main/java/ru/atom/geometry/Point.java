package ru.atom.geometry;

public class Point implements Collider {
    private int x;
    private int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if(other.getClass() == Point.class) {
            Point temp = (Point) other;
            return this.equals(temp);
        }
        if(other.getClass() == Bar.class) {
            Bar temp = (Bar) other;
            return (x >= temp.getMinX()) && (x <= temp.getMaxX())
                    && (y >= temp.getMinY()) && (y <= temp.getMaxY());
        }
        return false;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return  (this.x == point.x) && (this.y == point.y);
    }
}
