package ru.atom.gameserver.geometry;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Bar implements Collider {

    private final Point originCorner;
    private final Point endCorner;

    /*                  endCorner
                    +--*
                    | /|
                    |/ |
                    *--+
        originCorner
     */

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        originCorner = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
        endCorner = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
    }

    public Bar(Point point, int width, int height) {
        originCorner = point;
        endCorner = new Point(point.getX() + width, point.getY() + height);
    }

    public Point getOriginCorner() {
        return new Point(originCorner.getX(), originCorner.getY());
    }

    public Point getEndCorner() {
        return new Point(endCorner.getX(), endCorner.getY());
    }

    public float getWidth() {
        return endCorner.getX() - originCorner.getX();
    }

    public float getHeight() {
        return endCorner.getY() - originCorner.getY();
    }

    public boolean isIntersecting(Bar otherBar) {
        return Math.abs(originCorner.getX() - (otherBar.getOriginCorner().getX()))
                <= (originCorner.getX() < otherBar.getOriginCorner().getX() ? this.getWidth() : otherBar.getWidth())
                && Math.abs(originCorner.getY() - otherBar.getOriginCorner().getY())
                <= (originCorner.getY() < otherBar.getOriginCorner().getY() ? this.getHeight() : otherBar.getHeight());
    }

    public boolean isIncluding(Point point) {
        return originCorner.getX() <= point.getX() && point.getX() <= endCorner.getX()
                && originCorner.getY() <= point.getY() && point.getY() <= endCorner.getY();
    }

    public boolean isIncluding(Bar otherBar) {
        return isIncluding(otherBar.getOriginCorner()) && isIncluding(otherBar.getEndCorner());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return isIntersecting((Bar)other);
        } else /*if (other instanceof Point)*/ {
            return isIncluding((Point)other);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return originCorner.equals(bar.originCorner) && endCorner.equals(bar.endCorner);
    }
}
