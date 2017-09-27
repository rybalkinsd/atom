package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isColliding(Collider o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() == o.getClass())
            return equals(o);
        else {
            Bar bar = (Bar) o;
            if ((((x >= bar.x1) && (x <= bar.x2)) || ((x >= bar.x2) && (x <= bar.x1))) && (((y >= bar.y1)
                    && (y <= bar.y2)) || ((y >= bar.y2) && (y <= bar.y1))))
                return true;
            return false;
        }
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

        // your code here
        if ((point.x == x) && (point.y == y))
            return true;
        return false;
    }
}
