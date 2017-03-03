package ru.atom.geometry;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int x1, int y1, int x2, int y2) {
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
            boolean xcollide;
            boolean ycollide;
            Point point = (Point) other;
            if (this.firstCornerX > this.secondCornerX) {
                if (point.xCoor <= this.firstCornerX && point.xCoor >= this.secondCornerX) {
                    xcollide = true;
                } else {
                    xcollide = false;
                }
            } else {
                if (point.xCoor >= this.firstCornerX && point.xCoor <= this.secondCornerX) {
                    xcollide = true;
                } else {
                    xcollide = false;
                }
            }
            if (this.firstCornerY > this.secondCornerY) {
                if (point.yCoor <= this.firstCornerY && point.yCoor >= this.secondCornerY) {
                    ycollide = true;
                } else {
                    ycollide = false;
                }
            } else {
                if (point.yCoor >= this.firstCornerY && point.yCoor <= this.secondCornerY) {
                    ycollide = true;
                } else {
                    ycollide = false;
                }
            }
            return xcollide && ycollide;
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
        int xleft1 = min(this.firstCornerX, this.secondCornerX);
        int xright1 = max(this.firstCornerX, this.secondCornerX);
        int ybot1 = min(this.firstCornerY, this.secondCornerY);
        int ytop1 = max(this.firstCornerY, this.secondCornerY);
        int xleft2 = min(other.firstCornerX, other.secondCornerX);
        int xright2 = max(other.firstCornerX, other.secondCornerX);
        int ybot2 = min(other.firstCornerY, other.secondCornerY);
        int ytop2 = max(other.firstCornerY, other.secondCornerY);
        return xleft1 == xleft2 && xright1 == xright2 && ybot1 == ybot2
                && ytop1 == ytop2;
    }
}
