package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private final int x;
    private final int y;

    Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getXy() {
        return new int[]{this.x,this.y};
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
        return  (x == point.getXy()[0] && y == point.getXy()[1]);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() ==  Point.class) {
            Point point = (Point) other;
            return this.equals(point);
        }
        if (other.getClass() == Bar.class) {
            Bar bar = (Bar) other;
            return bar.isColliding(this);
        }
        return false;
    }
}
