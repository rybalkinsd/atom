package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ru.atom.geometry.Collider;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
        return this.getX() == point.getX()
                && this.getY() == point.getY();
    }

    public boolean isColliding(Collider o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Collider)) return false;

        if (o instanceof Point) {
            Point point = (Point) o;
            return this.getX() == point.getX()
                    && this.getY() == point.getY();
        } else if (o instanceof Bar) {
            Bar bar = (Bar) o;
            return (bar.getFirstCornerX() <= this.x
                    && this.x <= bar.getSecondCornerX()
                    && bar.getFirstCornerY() <= this.y
                    && this.y <= bar.getSecondCornerY());
        } else return false;
        // fix colliding for Bar
    }
}
