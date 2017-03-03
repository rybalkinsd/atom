package ru.atom.geometry;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar (int x1, int y1, int x2, int y2) {
        this.firstCornerX = x1;
        this.firstCornerY = y1;
        this.secondCornerX = x2;
        this.secondCornerY = y2;
    }

    public Point[] getCorners(Bar bar) {
        Point[] barCorner = new Point[4];
        barCorner[0] = new Point(bar.firstCornerX, bar.firstCornerY);
        barCorner[1] = new Point(bar.firstCornerX, bar.secondCornerY);
        barCorner[2] = new Point(bar.secondCornerX, bar.firstCornerY);
        barCorner[3] = new Point(bar.secondCornerX, bar.secondCornerY);
        return barCorner;
    }

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            boolean xCollide;
            boolean yCollide;
            Point point = (Point) other;
            if (this.firstCornerX > this.secondCornerX) {
                if (point.xCoor <= this.firstCornerX && point.xCoor >= this.secondCornerX) {
                    xCollide = true;
                } else {
                    xCollide = false;
                }
            } else {
                if (point.xCoor >= this.firstCornerX && point.xCoor <= this.secondCornerX) {
                    xCollide = true;
                } else {
                    xCollide = false;
                }
            }
            if (this.firstCornerY > this.secondCornerY) {
                if (point.yCoor <= this.firstCornerY && point.yCoor >= this.secondCornerY) {
                    yCollide = true;
                } else {
                    yCollide = false;
                }
            } else {
                if (point.yCoor >= this.firstCornerY && point.yCoor <= this.secondCornerY) {
                    yCollide = true;
                } else {
                    yCollide = false;
                }
            }
            return xCollide && yCollide;
        } else {
            Bar bar = (Bar) other;
            Point[] barCorners = getCorners(bar);
            for (Point i : barCorners) {
                if (this.isColliding(i)) {
                    return true;
                }
            }
            barCorners = getCorners(this);
            for (Point i : barCorners) {
                if (bar.isColliding(i)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        Bar other = (Bar) o;
        int xLeft1 = min(this.firstCornerX, this.secondCornerX);
        int xRight1 = max(this.firstCornerX, this.secondCornerX);
        int yBot1 = min(this.firstCornerY, this.secondCornerY);
        int yTop1 = max(this.firstCornerY, this.secondCornerY);
        int xLeft2 = min(other.firstCornerX, other.secondCornerX);
        int xRight2 = max(other.firstCornerX, other.secondCornerX);
        int yBot2 = min(other.firstCornerY, other.secondCornerY);
        int yTop2 = max(other.firstCornerY, other.secondCornerY);
        return xLeft1 == xLeft2 && xRight1 == xRight2 && yBot1 == yBot2
                && yTop1 == yTop2;
    }
}
