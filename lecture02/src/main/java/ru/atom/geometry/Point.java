package ru.atom.geometry;

public class Point implements Collider {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == getClass()) {
            return equals(other);
        }
        throw new UnsupportedOperationException();
    }
}
