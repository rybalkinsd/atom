package ru.atom.geometry;

public class Point implements Collider {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point convertToBitmapPosition() {
        return new Point(x / 32, y / 32);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getRightPoint(int i) {
        return new Point(x + 32 * i, y);
    }

    public Point getLeftPoint(int i) {
        return new Point(x - 32 * i, y);
    }

    public Point getUpperPoint(int i) {
        return new Point(x, y + 32 * i);
    }

    public Point getLowerPoint(int i) {
        return new Point(x, y - 32 * i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x == point.x && y == point.y) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
