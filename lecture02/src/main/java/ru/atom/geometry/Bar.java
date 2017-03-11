package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Bar implements Collider {

    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int x1, int y1, int x2, int y2) {
        this.firstPointX = x1;
        this.firstCornerY = y1;
        this.secondCornerX = x2;
        this.secondCornerY = y2;
    }

    public int getX1() {
        return firstPointX;
    }

    public int getY1() {
        return firstCornerY;
    }

    public int getX2() {
        return secondCornerX;
    }

    public int getY2() {
        return secondCornerY;
    }

    public int getXLeftLowofBar() {
        int x1;
        if (this.getX1() > this.getX2()) {
            x1 = this.getX2();
        } else {
            x1 = this.getX1();
        }
        return x1;
    }

    public int getYLeftLowofBar() {
        int y1;
        if (this.getY1() > this.getY2()) {
            y1 = this.getY2();
        } else {
            y1 = this.getY1();
        }
        return y1;
    }

    public int getXRightUpofBar() {
        int x2;
        if (this.getX1() > this.getX2()) {
            x2 = this.getX1();
        } else {
            x2 = this.getX2();
        }
        return x2;
    }

    public int getYRightUpofBar() {
        int y2;
        if (this.getY1() > this.getY2()) {
            y2 = this.getY1();
        } else {
            y2 = this.getY2();
        }
        return y2;
    }

    public int getXRightLowofBar() {
        int x3;
        x3 = getXRightUpofBar();
        return x3;
    }

    public int getYRightLowofBar() {
        int y3;
        y3 = getYLeftLowofBar();
        return y3;
    }

    public int getXLeftUpofBar() {
        int x4;
        x4 = getXLeftLowofBar();
        return x4;
    }

    public int getYLeftUpofBar() {
        int y4;
        y4 = getYRightUpofBar();
        return y4;
    }

    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return (((Bar) other).equals(this)
                    || !(((Bar) other).getXLeftLowofBar() > this.getXRightUpofBar()
                    || ((Bar) other).getYLeftLowofBar() > this.getYRightUpofBar()
                    || this.getXLeftLowofBar() > ((Bar) other).getYRightUpofBar()
                    || this.getYLeftLowofBar() > ((Bar) other).getYRightUpofBar()));
        }
        if (other instanceof Point) {
            return ((Point) other).isColliding(this);
        }
        return false;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        int x1;
        int y1;
        int x2;
        int y2;
        if (this.getX1() > this.getX2()) {
            x1 = this.getX2();
            x2 = this.getX1();
        } else {
            x1 = this.getX1();
            x2 = this.getX2();
        }
        if (this.getY1() > this.getY2()) {
            y1 = this.getY2();
            y2 = this.getY1();
        } else {
            y2 = this.getY2();
            y1 = this.getY1();
        }
        int xbar1;
        int ybar1;
        int xbar2;
        int ybar2;
        if (bar.getX1() > bar.getX2()) {
            xbar1 = bar.getX2();
            xbar2 = bar.getX1();
        } else {
            xbar1 = bar.getX1();
            xbar2 = bar.getX2();
        }
        if (bar.getY1() > bar.getY2()) {
            ybar1 = bar.getY2();
            ybar2 = bar.getY1();
        } else {
            ybar2 = bar.getY2();
            ybar1 = bar.getY1();
        }
        int x3;
        x3 = x2;
        int y3;
        y3 = y1;
        int x4;
        x4 = x1;
        int y4;
        y4 = y2;
        int xbar3;
        int ybar3;
        int xbar4;
        xbar3 = xbar2;
        ybar3 = ybar1;
        xbar4 = xbar1;
        int ybar4;
        ybar4 = ybar2;
        if (x1 == xbar1 && x2 == xbar2 && x3 == xbar3 && x4 == xbar4
                && y1 == ybar1 && y2 == ybar2 && y3 == ybar3 && y4 == ybar4) {
            return true;
        } else return false;
    }
}