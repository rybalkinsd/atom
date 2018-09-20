package ru.atom.geometry;

public class Bar implements Collider {

    Point leftBottom;
    Point rightTop;

    public Bar(Point leftBottom, Point rightTop) {
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.leftBottom = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
        this.rightTop = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bar)) {
            return false;
        }

        Bar other = (Bar) object;
        return this.leftBottom.equals(other.leftBottom) && this.rightTop.equals(other.rightTop);
    }

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return other.isColliding(this);
        } else if (other instanceof Bar) {
            return leftBottom.getY() <= ((Bar) other).getRightTop().getY()
                    && rightTop.getY() >= ((Bar) other).getLeftBottom().getY()
                    && leftBottom.getX() <= ((Bar) other).getRightTop().getX()
                    && rightTop.getX() >= ((Bar) other).getLeftBottom().getX();
        } else {
            return false;
        }
    }
}