package ru.atom.geometry;


/**
 * Created by dmbragin on 3/1/17.
 */
public class Bar implements Collider {

    private Point leftCornerPoint;

    private Point rightCornerPoint;

    public Bar(int x1, int y1, int x2, int y2) {
        regeneratePoints(x1, y1, x2, y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return leftCornerPoint.equals(bar.getLeftCornerPoint())
                && rightCornerPoint.equals(bar.getRightCornerPoint());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;

            return (leftCornerPoint.getX() <= point.getX()
                    && leftCornerPoint.getY() <= point.getY()
                    && rightCornerPoint.getX() >= point.getX()
                    && rightCornerPoint.getY() >= point.getY());
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;

            return !((rightCornerPoint.getX() < bar.getLeftCornerPoint().getX())
                    || (leftCornerPoint.getX() > bar.getRightCornerPoint().getX())
                    || (rightCornerPoint.getY() < bar.getLeftCornerPoint().getY())
                    || (leftCornerPoint.getY() > bar.getRightCornerPoint().getY()));
        }
        throw new IllegalArgumentException();
    }

    public Point getLeftCornerPoint() {
        return leftCornerPoint;
    }

    public Point getRightCornerPoint() {
        return rightCornerPoint;
    }

    /**
     * This func use for set left bottom corner and right top corner.
     */
    private void regeneratePoints(int x1, int y1, int x2, int y2) {
        if (x2 < x1) {
            int tmp = x2;
            x2 = x1;
            x1 = tmp;
        }
        if (y2 < y1) {
            int tmp = y2;
            y2 = y1;
            y1 = tmp;
        }

        leftCornerPoint = new Point(x1, y1);
        rightCornerPoint = new Point(x2, y2);
    }
}
