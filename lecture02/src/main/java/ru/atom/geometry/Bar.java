package ru.atom.geometry;

/**
 * Template class for
 */
public class Bar implements Collider {
    private Point left;
    private Point right;

    public Point getLeft() {
        return left;
    }

    public Point getRight() {
        return right;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return (left.equals(bar.left)) && (right.equals(bar.right));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            if ((left.getX() > bar.right.getX()) || (right.getX() < bar.left.getX())) {
                return false;
            }

            if ((left.getY() > bar.right.getY()) || (right.getY() < bar.left.getY())) {
                return false;
            }

            return true;
        } else {
            Point point = (Point) other;
            return ((point.getX() >= left.getX()) && (point.getX() <= right.getX())
                    && (point.getY() >= left.getY()) && (point.getY() <= right.getY()));
        }
    }

    public Bar(int xx1, int yy1, int xx2, int yy2) {
        left = new Point(Math.min(xx1, xx2), Math.min(yy1, yy2));
        right = new Point(Math.max(xx1, xx2), Math.max(yy1, yy2));
    }
}
