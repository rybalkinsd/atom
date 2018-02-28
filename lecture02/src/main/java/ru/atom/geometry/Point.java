package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {

    private final double x;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return equals(other);
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return (bar.getDownLeftPoint().getX() <= x
                    && bar.getDownLeftPoint().getY() <= y
                    && bar.getTopRightPoint().getX() >= x
                    && bar.getTopRightPoint().getY() >= y);
        }
        return false;
    }


}
