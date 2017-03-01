package ru.atom.geometry;


/**
 * Created by dmbragin on 3/1/17.
 */
public class Bar implements Collider {

    private Point leftCornerPoint;

    private Point rightCornerPoint;


    public Bar(int x1, int y1, int x2, int y2) {
        this.leftCornerPoint = new Point(x1, y1);
        this.rightCornerPoint = new Point(x2, y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return (leftCornerPoint == bar.getLeftCornerPoint() && rightCornerPoint == bar.getRightCornerPoint());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point) other;

            return (leftCornerPoint.getX() <= point.getX()
                    && leftCornerPoint.getY() <= point.getY()
                    && rightCornerPoint.getX() >= point.getX()
                    && rightCornerPoint.getY() >= point.getY());
        }
        if (other.getClass() == Point.class) {
            Bar bar = (Bar) other;
            return false;
        }
        throw new IllegalArgumentException();
    }

    public Point getLeftCornerPoint() {
        return leftCornerPoint;
    }

    public Point getRightCornerPoint() {
        return rightCornerPoint;
    }
}
