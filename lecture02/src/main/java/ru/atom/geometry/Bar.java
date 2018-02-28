package ru.atom.geometry;

public class Bar implements Collider {

    private final Point topRight;
    private final Point bottomLeft;

    public Bar(Point top, Point bottom) {
        this.topRight = top;
        this.bottomLeft = bottom;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.topRight = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
        this.bottomLeft = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
    }

    public Point getTopRight() {
        return topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        return this.topRight.equals(bar.getTopRight()) && this.bottomLeft.equals(bar.getBottomLeft());
    }
    
    @Override
    public boolean isColliding(Collider other) {
        if (Point.class.isInstance(other)) {
            Point point = (Point) other;
            return (this.topRight.getX() >= point.getX() && this.topRight.getY() >= point.getY()
                    && this.bottomLeft.getX() <= point.getX() && this.bottomLeft.getY() <= point.getY());
        } else if (Bar.class.isInstance(other)) {
            Bar bar = (Bar) other;
            return (this.isColliding(bar.getTopRight()) || this.isColliding(bar.getBottomLeft())
                    || bar.isColliding(this.topRight) || bar.isColliding(this.bottomLeft));
        }
        return false;
    }

}
