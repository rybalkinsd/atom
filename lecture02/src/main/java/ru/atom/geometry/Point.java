package ru.atom.geometry;

/**
 * Template class for Point
 */
public class Point implements Collider {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    *   @return x value
    * */
    public int getX() {
        return x;
    }

    /**
     *   @return y value
     * */
    public int getY() {
        return y;
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
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return (this.x >= bar.getFirstCornerPoint().getX() && this.x <= bar.getSecondCornerPoint().getX())
                    && (this.y >= bar.getFirstCornerPoint().getY() && this.y <= bar.getSecondCornerPoint().getY());
        }

        return false;
    }
}