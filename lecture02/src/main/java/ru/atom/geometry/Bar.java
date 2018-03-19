package ru.atom.geometry;

public class Bar implements Collider {
    private Point firstCorner;
    private Point secondCorner;

    public Bar(Point pfirstCorner, Point psecondCorner) {
        if (pfirstCorner.getX() < psecondCorner.getX() && pfirstCorner.getY() < psecondCorner.getY()) {
            firstCorner = pfirstCorner;
            secondCorner = psecondCorner;
        } else if (pfirstCorner.getX() > psecondCorner.getX() && pfirstCorner.getY() > psecondCorner.getY()) {
            firstCorner = psecondCorner;
            secondCorner = pfirstCorner;
        } else if (pfirstCorner.getX() < psecondCorner.getX() && pfirstCorner.getY() > psecondCorner.getY()) {
            firstCorner = new Point(pfirstCorner.getX(), psecondCorner.getY());
            secondCorner = new Point(psecondCorner.getX(), pfirstCorner.getY());
        } else if (pfirstCorner.getX() > psecondCorner.getX() && pfirstCorner.getY() < psecondCorner.getY()) {
            firstCorner = new Point(psecondCorner.getX(), pfirstCorner.getY());
            secondCorner = new Point(pfirstCorner.getX(), psecondCorner.getY());
        }
    }

    private boolean point_in_segment(int x1, int x2, int p)  {
        return x1 <= p && p <= x2;
    }

    private boolean intersect_of_segments(int x11, int x12, int x21, int x22) {
        if (point_in_segment(x11,x12,x21) || point_in_segment(x11,x12,x22) || point_in_segment(x21, x22, x11)
                || point_in_segment(x21, x22, x12)) return true;
        return false;
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            if (point.getY() < firstCorner.getY() || point.getY() > secondCorner.getY())
                return false;
            if (point.getX() < firstCorner.getX() || point.getX() > secondCorner.getX())
                return false;
            return true;
        } else if (other.getClass() == getClass()) {
            Bar bar = (Bar) other;
            if (intersect_of_segments(firstCorner.getX(),secondCorner.getX(),
                bar.firstCorner.getX(), bar.secondCorner.getX())
                && intersect_of_segments(firstCorner.getY(), secondCorner.getY(),
                bar.firstCorner.getY(), bar.secondCorner.getY())) return true;
            return false;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return bar.firstCorner.equals(firstCorner) && bar.secondCorner.equals(secondCorner);
    }
}
