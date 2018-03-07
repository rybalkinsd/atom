package ru.atom.geometry;

public class Point implements Collider /* super class and interfaces here if necessary */ {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (this.x == point.x && this.y == point.y)
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider o) {
        Point point = (Point) o;

        if (this.equals(point))
            return true;
        else
            return false;
    }
}
