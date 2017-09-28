package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    final int x;
    final int y;
    // and methods

    public
        Point(int x1, int y1) {
        x = x1;
        y = y1;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o1) {
        if (this == o1)
            return true;
        if (o1 == null || getClass() != o1.getClass())
            return false;

        // cast from Object to Point
        Point o2 = (Point) o1;

        // your code here
        if ((o2.x == x) && (o2.y == y))
            return true;
        return false;
    }


    public boolean isColliding(Collider o1) {
        if (this == o1) return true;
        if (o1 == null) return false;

        if (getClass() == o1.getClass())
            return equals(o1);
        else {
            Bar o2 = (Bar) o1;
            if ((((x >= o2.x1) && (x <= o2.x2)) || ((x >= o2.x2) && (x <= o2.x1)))
                        && (((y >= o2.y1) && (y <= o2.y2)) || ((y >= o2.y2) && (y <= o2.y1))))
                return true;
            return false;
        }
    }
}
