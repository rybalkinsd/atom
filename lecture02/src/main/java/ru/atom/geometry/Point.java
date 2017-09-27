package ru.atom.geometry;


public class Point implements Collider {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isColliding(Collider otherPoint) {
        return this.equals(otherPoint);
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
        return x == point.x && y == point.y;
        // your code here
        // throw new UnsupportedOperationException();
    }
}
