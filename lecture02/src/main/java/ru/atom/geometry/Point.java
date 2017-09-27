package ru.atom.geometry;

public class Point implements Collider {
    private int x;
    private int y;

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

        // cast from Object to Point
        Point point = (Point)o;

        return point.getX() == x && point.getY() == y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point)other;
            return x == point.getX() && y == point.getY();
        } else if (other instanceof Bar) {
            Bar bar = (Bar)other;

            int leftX  = Math.min(bar.getFirstPoint().getX(), bar.getSecondPoint().getX());
            int leftY  = Math.min(bar.getFirstPoint().getY(), bar.getSecondPoint().getY());
            int rightX = Math.max(bar.getFirstPoint().getX(), bar.getSecondPoint().getX());
            int rightY = Math.max(bar.getFirstPoint().getY(), bar.getSecondPoint().getY());

            return leftX <= x && x <= rightX && leftY <= y && y <= rightY;
        }

        return false;
    }
}
