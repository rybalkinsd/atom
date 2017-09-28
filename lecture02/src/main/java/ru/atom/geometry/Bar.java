package ru.atom.geometry;


public class Bar implements Collider {

    private Point startCorner;
    private Point finishCorner;

    public Bar(int startCornerX, int startCornerY, int finishCornerX, int finishCornerY) {
        startCorner = new Point(startCornerX, startCornerY);
        finishCorner = new Point(finishCornerX, finishCornerY);
    }

    public Point getStartCorner() {
        return new Point(startCorner.getX(), startCorner.getY());
    }

    public Point getFinishCorner() {
        return new Point(finishCorner.getX(), finishCorner.getY());
    }

    public int getWidth() {
        return finishCorner.getX() - startCorner.getX();
    }

    public int getHeight() {
        return finishCorner.getY() - startCorner.getY();
    }

    public boolean isIntersects(Bar otherBar) {
        int width = startCorner.getX() < otherBar.getStartCorner().getX() ? this.getWidth() : otherBar.getWidth();
        int height = startCorner.getY() < otherBar.getStartCorner().getY() ? this.getHeight() : otherBar.getHeight();
        return Math.abs(startCorner.getX()  - otherBar.getStartCorner().getX()) <= width
                && Math.abs(startCorner.getY() - otherBar.getStartCorner().getY()) <= height;
    }

    public boolean hasInto(Point point) {
        return point.getX() <= startCorner.getX() + this.getWidth()
                && point.getY() <= startCorner.getY() + this.getHeight();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return isIntersects((Bar)other);
        } else /*if (other instanceof Point)*/ {
            return hasInto((Point)other);
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
