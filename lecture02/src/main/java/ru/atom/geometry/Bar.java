package ru.atom.geometry;

public class Bar implements Collider {
    //fields
    private int minY;
    private int maxY;
    private int maxX;
    private int minX;
    //methods


    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    private static int max(int a, int b) {
        if (a > b)
            return a;
        else
            return b;
    }

    private static int min(int a, int b) {
        if (a < b)
            return a;
        else
            return b;
    }

    public Bar(int x1, int y1, int x2, int y2) {
        this.minX = min(x1,x2);
        this.minY = min(y1,y2);
        this.maxX = max(x1,x2);
        this.maxY = max(y1,y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        // your code here
        if ((this.maxY == bar.maxY) && (this.maxX == bar.maxX)
                && (this.minX == bar.minX) && (this.minY == bar.minY))
            return true;
        else
            return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if ((this.maxY < bar.minY) || (this.minY > bar.maxY)
                    || (this.maxX < bar.minX) || (this.minX > bar.maxX))
                return false;
            else
                return true;
        }
        if (other instanceof Point) {
            Point point = (Point) other;
            int pointX = point.getX();
            int pointY = point.getY();
            if ((this.maxY < pointY) || (this.minY > pointY)
                    || (this.maxX < pointX) || (this.minX > pointX))
                return false;
            else
                return true;
        }
        return false;
    }


}
