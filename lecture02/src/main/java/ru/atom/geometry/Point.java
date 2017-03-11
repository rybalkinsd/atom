package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {

    private int x;
    private int y;

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

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return other.equals(this);
        }
        if (other instanceof Bar) {
            return (((Bar) other).getXLeftLowofBar() <= this.getX()
                    && ((Bar) other).getYLeftLowofBar() <= this.getY()
                    && ((Bar) other).getXLeftUpofBar() <= this.getX()
                    && ((Bar) other).getYLeftUpofBar() >= this.getY()
                    && ((Bar) other).getXRightUpofBar() >= this.getX()
                    && ((Bar) other).getYRightUpofBar() >= this.getY()
                    && ((Bar) other).getXRightLowofBar() >= this.getX()
                    && ((Bar) other).getYRightLowofBar() <= this.getY());
        }
        return false;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if ((this.getY() == point.getY() && (this.getX() == point.getX()))) return true;
        return false;
    }
}
