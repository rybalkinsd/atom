package ru.atom.geometry;

public class Bar implements Collider {
    private Point first_corner;
    private Point second_corner;

    public Point getFirst_corner() {
        return first_corner;
    }

    public Point getSecond_corner() {
        return second_corner;
    }

    public Bar (Point first_corner_, Point second_corner_) {
        if (first_corner_.getX() < second_corner_.getX() && first_corner_.getY() < second_corner_.getY()) {
            first_corner = first_corner_;
            second_corner = second_corner_;
        }
        else if (first_corner_.getX() > second_corner_.getX() && first_corner_.getY() > second_corner_.getY()) {
            first_corner = second_corner_;
            second_corner = first_corner_;
        }
        else if (first_corner_.getX() < second_corner_.getX() && first_corner_.getY() > second_corner_.getY()) {
            first_corner = new Point(first_corner_.getX(), second_corner_.getY());
            second_corner = new Point(second_corner_.getX(), first_corner_.getY());
        }
        else if (first_corner_.getX() > second_corner_.getX() && first_corner_.getY() < second_corner_.getY()) {
            first_corner = new Point(second_corner_.getX(), first_corner_.getY());
            second_corner = new Point(first_corner_.getX(), second_corner_.getY());
        }
    }

    private boolean point_in_segment(int x1, int x2, int p)  {
        return x1 <= p && p <= x2;
    }

    private boolean intersect_of_segments(int x1, int x2, int x_1, int x_2) {
        if (point_in_segment(x1,x2,x_1) || point_in_segment(x1,x2,x_2) || point_in_segment(x_1, x_2, x1)
                || point_in_segment(x_1, x_2, x2)) return true;
        return false;
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            if (point.getY() < first_corner.getY() || point.getY() > second_corner.getY())
                return false;
            if (point.getX() < first_corner.getX() || point.getX() > second_corner.getX())
                return false;
            return true;
        }
        else if (other.getClass() == getClass()) {
            Bar bar = (Bar) other;
            if (intersect_of_segments(first_corner.getX(),second_corner.getX(),
                bar.first_corner.getX(), bar.second_corner.getX()) &&
                intersect_of_segments(first_corner.getY(), second_corner.getY(),
                bar.first_corner.getY(), bar.second_corner.getY())) return true;
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
        return bar.first_corner.equals(first_corner) && bar.second_corner.equals(second_corner);
    }
}
