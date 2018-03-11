package ru.atom.geometry;

/**
 * Template class for
 */
public class Bar implements Collider {
    private Point Left;
    private Point Right;

    private boolean intersection(int a1, int a2, int b1, int b2) {
        return !((Math.min(a1,a2) > Math.max(b1,b2)) || (Math.min(b1,b2) > Math.max(a1,a2)));
    }

    public Point getLeft() {
        return Left;
    }

    public Point getRight() {
        return Right;
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

        return (Left.equals(bar.Left)) && (Right.equals(bar.Right));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            if ((Left.getX() > bar.Right.getX()) || (Right.getX() < bar.Left.getX())) {
                return false;
            }

            if ((Left.getY() > bar.Right.getY()) || (Right.getY() < bar.Left.getY())) {
                return false;
            }

            return true;
        } else {
            Point point = (Point) other;
            return ((point.getX() >= Left.getX()) && (point.getX() <= Right.getX())
                    && (point.getY() >= Left.getY()) && (point.getY() <= Right.getY()));
        }
    }

    public Bar(int xx1, int yy1, int xx2, int yy2) {
        Left = new Point(Math.min(xx1, xx2), Math.min(yy1, yy2));
        Right = new Point(Math.max(xx1, xx2), Math.max(yy1, yy2));
    }
}
