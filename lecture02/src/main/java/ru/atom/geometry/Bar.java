package ru.atom.geometry;

public class Bar implements Collider {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Bar(int a, int b, int c, int d) {

        x1 = max(a, c);
        x2 = min(a, c);
        y1 = max(b, d);
        y2 = min(b, d);

    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
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

    @Override
    public boolean isColliding(Collider o) {

        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Point) {
            Point point = (Point) o;
            int pointX = point.getX();
            int pointY = point.getY();
            if ((pointX > x1) || (pointX < x2) || (pointY > y1) || (pointY < y2))
                return false;
            else
                return true;

        }
        if (o instanceof Bar) {
            // cast from Object to Bar
            Bar bar = (Bar) o;

            if ((y1 < bar.y2) || (bar.y1 < y2) || (x1 < bar.x2) || (bar.x1 < x2))
                return false;
            else
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        if ((x1 == bar.x1) && (x2 == bar.x2) && (y1 == bar.y1) && (y2 == bar.y2))
            return true;
        else
            return false;

    }
}
