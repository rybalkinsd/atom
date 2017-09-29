package ru.atom.geometry;


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

    public Point getOriginCorner() {
        return new Point(originCorner.getX(), originCorner.getY());
    }

    public Point getEndCorner() {
        return new Point(endCorner.getX(), endCorner.getY());
    }

    public int getWidth() {
        return endCorner.getX() - originCorner.getX();
    }

    public int getHeight() {
        return endCorner.getY() - originCorner.getY();
    }

    public boolean isIntersecting(Bar otherBar) {
        return Math.abs(originCorner.getX() - (otherBar.getOriginCorner().getX()))
                <= (originCorner.getX() < otherBar.getOriginCorner().getX() ? this.getWidth() : otherBar.getWidth())
                && Math.abs(originCorner.getY() - otherBar.getOriginCorner().getY())
                <= (originCorner.getY() < otherBar.getOriginCorner().getY() ? this.getHeight() : otherBar.getHeight());
    }

    public boolean isIncluding(Point point) {
        return point.getX() <= originCorner.getX() + this.getWidth()
                && point.getY() <= originCorner.getY() + this.getHeight();
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
        return getWidth() == bar.getWidth() && getHeight() == bar.getHeight();
    }

}
