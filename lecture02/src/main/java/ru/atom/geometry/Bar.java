package ru.atom.geometry;

public class Bar implements Collider {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    Bar() {
        minX = 0;
        maxX = 0;
        minY = 0;
        maxY = 0;
    }

    Bar(int X1, int Y1, int X2, int Y2) {
        minX = Math.min(X1,X2);
        maxX = Math.max(X1, X2);
        minY = Math.min(Y1, Y2);
        maxY = Math.max(Y1, Y2);
    }

    @Override
    public boolean isColliding(Collider other) {
        if(other.getClass() == Point.class) {
            Point temp = (Point) other;
            return (temp.getX() >= minX) && (temp.getX() <= maxX) &&
                    (temp.getY() >= minY) && (temp.getY() <= maxY);
        }
        if(other.getClass() == Bar.class) {
            Bar temp = (Bar) other;
            return !((minX > temp.getMaxX()) || (maxX < temp.getMinX()) || (minY > temp.getMaxY()) || (maxY < temp.getMinY()));
        }
        return false;
    }

    public int getMinX() {
        return minX;
    }
    public int getMaxX() {
        return maxX;
    }
    public int getMinY() {
        return minY;
    }
    public int getMaxY() {
        return maxY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return (this.minX == bar.minX) && (this.maxX == bar.maxX) &&
                (this.minY == bar.minY) && (this.maxY == bar.maxY);
    }
}
