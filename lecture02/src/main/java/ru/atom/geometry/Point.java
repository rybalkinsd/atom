package ru.atom.geometry;

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

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return pointInBar(this, bar);
        }
        return false;
    }

    public static boolean pointInBar(Point point, Bar bar) {
        int x = point.x;
        int y = point.y;
        int minX = bar.getX1().x;
        int maxX = bar.getX2().x;
        int minY = bar.getX1().y;
        int maxY = bar.getX2().y;
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
