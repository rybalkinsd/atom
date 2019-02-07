package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    private int x;
    private int y;

    // and methods

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
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

        Point point = (Point) o;

        if (x == point.getX() && y == point.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            if (this.equals(other)) {
                return true;
            }
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point topLeftPoint = bar.getTopLeftPoint();
            Point botRightPoint = bar.getBotRightPoint();
            if ((x >= topLeftPoint.getX())
                    && (x <= botRightPoint.getX())
                    && (y <= topLeftPoint.getY())
                    && (y >= botRightPoint.getY())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
