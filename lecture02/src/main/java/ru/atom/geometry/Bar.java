package ru.atom.geometry;

/**
 * Created by alex on 03.03.17.
 */
public class Bar implements Collider {
    protected int firstPointX;
    protected int firstCornerY;
    protected int secondCornerX;
    protected int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = Math.min(firstPointX , secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(firstPointX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
    }

    public int getFirstPointX() {
        return firstPointX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    public int centerOfBarX() {
        return getFirstPointX()
                + (getSecondCornerX() - getFirstCornerY()) / 2;
    }

    public int centerOfBarY() {
        return getFirstCornerY()
            + (getSecondCornerY() - getFirstCornerY()) / 2;
    }

    public int lengthOfBarX() {
        return getSecondCornerX() - getFirstCornerY();
    }

    public int lengthOfBarY() {
        return getSecondCornerY() - getFirstCornerY();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bar bar = (Bar) o;

        return (this.centerOfBarX() == bar.centerOfBarX())
                && (this.centerOfBarY() == bar.centerOfBarY())
                && (this.lengthOfBarX() == bar.lengthOfBarX())
                && (this.lengthOfBarY() == bar.lengthOfBarY());

    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return ((this.firstPointX <= point.getX())
                    && (point.getX() <= this.secondCornerX)
                    && (this.firstCornerY <= point.getY())
                    && (point.getY() <= this.secondCornerY));
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return ((this.firstPointX <= bar.getFirstPointX())
                    && (bar.getFirstPointX() <= this.secondCornerX))
                    && ((this.firstCornerY <= bar.getFirstCornerY())
                    && (bar.getFirstCornerY() <= this.secondCornerY))
                    || (this.firstPointX <= bar.getSecondCornerX())
                    && (bar.getSecondCornerX() <= this.secondCornerX)
                    && (this.firstCornerY <= bar.getSecondCornerY())
                    && (bar.getSecondCornerY() <= this.secondCornerY);
        } else return false;

    }
}