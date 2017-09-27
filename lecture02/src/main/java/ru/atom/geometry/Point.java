package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(Point that) {
        x = that.x;
        y = that.y;
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
        if (this.x == point.x && this.y == point.y) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point)other;
            if (point.x == x && point.y == y) {
                return true;
            } else {
                return false;
            }
        } else if (other.getClass() == Bar.class) {
            Bar bar = (Bar) other;
            if (bar.isColliding(this)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
