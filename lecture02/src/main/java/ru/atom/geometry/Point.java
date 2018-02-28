package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    int x;
    int y;

    public boolean isColliding(Collider other) {
        return this.equals(other);
    }

    Point(int i,int j) {
        x = i;
        y = j;
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

        return (point.x == this.x && point.y == this.y);

    }
}
