package ru.atom.geometry;

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

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return isColliding(point);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.x == ((Point) other).getX() && this.y == ((Point) other).getY()) {
            return true;
        }

        return false;
    }
}
