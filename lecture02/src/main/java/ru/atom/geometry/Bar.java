package ru.atom.geometry;

import java.util.Objects;

public class Bar implements Collider {

    private final Point lowerLeft;
    private final Point upperRight;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        lowerLeft = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
        upperRight = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bar otherBar = (Bar) o;
        return Objects.equals(lowerLeft, otherBar.lowerLeft) && Objects.equals(upperRight, otherBar.upperRight);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.contains((Point) other);
        }
        if (other instanceof Bar) {
            return this.intersects((Bar) other);
        }
        return false;
    }

    private boolean intersects(Bar other) {
        double thisCenterX = average(lowerLeft.getX(), upperRight.getX());
        double otherCenterX = average(other.lowerLeft.getX(), other.upperRight.getX());
        if (Math.abs(thisCenterX - otherCenterX) > average(this.width(), other.width())) {
            return false;
        }

        double thisCenterY = average(lowerLeft.getY(), upperRight.getY());
        double otherCenterY = average(other.lowerLeft.getY(), other.upperRight.getY());
        return Math.abs(thisCenterY - otherCenterY) <= average(this.height(), other.height());
    }

    public int width() {
        return upperRight.getX() - lowerLeft.getX();
    }

    public int height() {
        return upperRight.getY() - lowerLeft.getY();
    }

    private double average(int from, int to) {
        return (from + to) / 2.0;
    }

    private boolean contains(Point point) {
        return isInside(point.getX(), lowerLeft.getX(), upperRight.getX())
                && isInside(point.getY(), lowerLeft.getY(), upperRight.getY());
    }

    private boolean isInside(int value, int start, int end) {
        return value >= start && value <= end;
    }
}
