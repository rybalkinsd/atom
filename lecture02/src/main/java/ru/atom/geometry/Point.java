package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {

    private final int x;
    private final int y;

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

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) return true;
        else {
            if (!(other instanceof Bar)) return false;
            Bar bar = (Bar) other;
            Point first = bar.getFirstPoint();
            Point second = bar.getSecondPoint();
            if (first.x <= this.x && second.x >= this.x && first.y <= this.y && second.y >= this.y) return true;
            else return false;
        }
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

        if (this.x == point.x && this.y == point.y) return true;
        else return false;


    }
}
