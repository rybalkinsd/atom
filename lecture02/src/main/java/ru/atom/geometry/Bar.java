package ru.atom.geometry;

import java.lang.Math;

public class Bar implements Collider {
    private final Point firstPoint;
    private final Point secondPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        firstPoint = new Point(Math.min(firstCornerX, secondCornerX),
                Math.min(firstCornerY, secondCornerY));
        secondPoint = new Point(Math.max(firstCornerX, secondCornerX),
                Math.max(firstCornerY, secondCornerY));
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) return other.isColliding(this);
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point first = bar.getFirstPoint();
            Point second = bar.getSecondPoint();
            if (this.secondPoint.getX() >= first.getX()
                    && this.secondPoint.getY() >= first.getY()
                    && this.firstPoint.getX() <= second.getX()
                    && this.firstPoint.getY() <= second.getY())
                return true;
            else return false;
        } else return false;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (firstPoint != null ? !firstPoint.equals(bar.firstPoint) : bar.firstPoint != null) return false;
        return secondPoint != null ? secondPoint.equals(bar.secondPoint) : bar.secondPoint == null;
    }

    @Override
    public int hashCode() {
        int result = firstPoint != null ? firstPoint.hashCode() : 0;
        result = 31 * result + (secondPoint != null ? secondPoint.hashCode() : 0);
        return result;
    }
}
