package ru.atom.geometry;

public class Bar implements Collider {
    private final Point first;
    private final Point second;

    public Point get_First() {
        return first;
    }

    public Point get_Second() {
        return second;
    }

    public Bar(int firstX, int firstY,
               int secondX, int secondY) {
        int maxx;
        int maxy;
        int minx;
        int miny;
        if (firstX > secondX) {
            maxx = firstX;
            minx = secondX;
        } else {
            maxx = secondX;
            minx = firstX;
        }
        if (firstY > secondY) {
            maxy = firstY;
            miny = secondY;
        } else {
            maxy = secondY;
            miny = firstY;
        }

        first = new Point(minx, miny);
        second = new Point(maxx, maxy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        Point barFirst = bar.get_First();
        Point barSecond = bar.get_Second();

        if (first.equals(barFirst))
            return second.equals(barSecond);
        else if (first.equals(barSecond))
            return second.equals(barFirst);

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        boolean flag = false;
        if (other instanceof Point) {
            Point point = (Point) other;
            flag = point.isColliding(this);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point firstBar = bar.get_First();
            Point secondBar = bar.get_Second();
            flag = firstBar.get_X() <= second.get_X() && secondBar.get_X() >= first.get_X()
                && second.get_Y() >= firstBar.get_Y() && secondBar.get_Y() >= first.get_Y();
        }
        return flag;
    }
}