package ru.atom.geometry;

public class Bar implements Collider {

    private Point leftDown;
    private Point rightUp;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int minX = (firstCornerX <= secondCornerX) ? firstCornerX : secondCornerX;
        int minY = (firstCornerY <= secondCornerY) ? firstCornerY : secondCornerY;
        int maxX = (firstCornerX <= secondCornerX) ? secondCornerX : firstCornerX;
        int maxY = (firstCornerY <= secondCornerY) ? secondCornerY : firstCornerY;
        this.leftDown = new Point(minX, minY);
        this.rightUp = new Point(maxX, maxY);
    }

    public Point getLeftDown() {
        return leftDown;
    }

    public Point getRightUp() {
        return rightUp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this == null || o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Bar bar = (Bar) o;
        return (this.getLeftDown().equals(bar.getLeftDown()) && this.getRightUp().equals(bar.getRightUp()));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            if (point.getX() > this.rightUp.getX() || point.getY() > this.rightUp.getY() || point.getX() < this.leftDown.getX() || point.getY() < this.leftDown.getY()) {
                return false;
            }
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (bar.equals(this)) {
                return true;
            }
            if (this.getRightUp().getX() < bar.getLeftDown().getX() || this.getRightUp().getY() < bar.getLeftDown().getY() || this.getLeftDown().getX() > bar.getRightUp().getX() || this.getLeftDown().getY() > bar.getRightUp().getY()) {
                return false;
            }
        }
        return true;
    }
}
