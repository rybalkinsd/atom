package ru.atom.geometry;

public class Bar implements Collider {

    private final Point leftPoint; //lower left corner
    private final Point rightPoint; //upper right corner


    public Bar(Point point, int x2, int y2) {
        leftPoint = point;
        rightPoint = new Point(x2, y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (leftPoint.equals(bar.leftPoint)
                && rightPoint.equals(bar.rightPoint)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass()) {
            Bar bar = (Bar) other;
            return leftPoint.getX() <= bar.rightPoint.getX()
                    && rightPoint.getX() >= bar.leftPoint.getX()
                    && leftPoint.getY() <= bar.rightPoint.getY()
                    && rightPoint.getY() >= bar.leftPoint.getY();
        } else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return leftPoint.getX() <= point.getX() && leftPoint.getY() <= point.getY()
                    && rightPoint.getX() >= point.getX() && rightPoint.getY() >= point.getY();
        } else throw new IllegalArgumentException();
    }

    public Point getLeftPoint() {
        return leftPoint;
    }

    public Point getRightPoint() {
        return rightPoint;
    }
}