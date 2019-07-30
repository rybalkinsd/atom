package ru.atom.geometry;

public class Bar implements Collider {

    private final Point leftTopCorner;
    private final int width;
    private final int height;

    public Bar(Point firstCorner, Point secondCorner) {
        int minX = Math.min(firstCorner.getX(), secondCorner.getX());
        int maxY = Math.max(firstCorner.getY(), secondCorner.getY());
        leftTopCorner = new Point(minX, maxY);
        width = Math.abs(firstCorner.getX() - secondCorner.getX());
        height = Math.abs(firstCorner.getY() - secondCorner.getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return contains((Point) other);
        }
        if (other instanceof Bar) {
            return intersects((Bar) other);
        }
        return false;
    }

    public boolean intersects(Bar other) {
        Point thisCenter = getCenter(this);
        Point otherCenter = getCenter(other);
        int xDist = thisCenter.getHorizontalDistanceTo(otherCenter);
        int yDist = thisCenter.getVerticalDistanceTo(otherCenter);
        return xDist <= (width + other.width) / 2 && yDist <= (height + other.height) / 2;
    }

    private static Point getCenter(Bar bar) {
        int x = bar.leftTopCorner.getX() + bar.width / 2;
        int y = bar.leftTopCorner.getY() - bar.height / 2;
        return new Point(x, y);
    }

    public boolean contains(Point point) {
        return point.getX() >= leftTopCorner.getX() &&
                point.getY() <= leftTopCorner.getY() &&
                point.getX() <= (leftTopCorner.getX() + width) &&
                point.getY() >= (leftTopCorner.getY() - height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bar)) {
            return false;
        }
        Bar otherBar = (Bar) o;
        return leftTopCorner.equals(otherBar.leftTopCorner) &&
                width == otherBar.width &&
                height == otherBar.height;
    }
}
