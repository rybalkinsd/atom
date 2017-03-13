package ru.atom.geometry;

//import java.awt.Rectangle;

public class Bar implements Collider {

    int xMax;
    int xMin;
    int yMax;
    int yMin;

    public Bar(int x1, int y1, int x2, int y2) {
        setBounds(x1, y1, x2, y2);

        // r = new Rectangle(xMin,yMin,xMax-xMin+1,yMax-yMin+1);
    }
    // Rectangle r;

    public int getXmax() {
        return xMax;
    }

    public int getXmin() {
        return xMin;
    }

    public int getYmax() {
        return yMax;
    }

    public int getYmin() {
        return yMin;
    }

    public void setBounds(int x1, int y1, int x2, int y2) {
        if (x1 <= x2) {
            xMax = x2;
            xMin = x1;
        } else {
            xMax = x1;
            xMin = x2;
        }
        if (y1 <= y2) {
            yMax = y2;
            yMin = y1;
        } else {
            yMax = y1;
            yMin = y2;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bar b2 = (Bar) o;

        if (xMax == b2.xMax && xMin == b2.xMin && yMax == b2.yMax && yMin == b2.yMin) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {

        if (other == null) {
            return false;
        }

        if (other.getClass() == Point.class) {
            return isColliding((Point) other);
        } else {
            if (this == other)
                return true;
            if (other == null || getClass() != other.getClass())
                return false;

            return isColliding((Bar) other);
        }
    }

    public boolean isColliding(Bar b2) {
        // return r.intersects(b2.r);

        if (isColliding(b2.xMax, b2.yMax) || isColliding(b2.xMax, b2.yMin)
            || isColliding(b2.xMin, b2.yMax) || isColliding(b2.xMin, b2.yMin)) {
            return true;
        } else if (isColliding(b2, xMax, yMax) || isColliding(b2, xMax, yMin)
            || isColliding(b2, xMin, yMax) || isColliding(b2, xMin, yMin)) {
            return true;
        } else if ((b2.yMin <= yMin) && (yMax <= b2.yMax) && (xMin <= b2.xMin)
            && (b2.xMax <= xMax)) {
            return true;
        } else if ((yMin <= b2.yMin) && (b2.yMax <= yMax) && (b2.xMin <= xMin)
            && (xMax <= b2.xMax)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isColliding(Point p1) {

        return ((xMin <= p1.x) && (p1.x <= xMax) && (yMin <= p1.y) && (p1.y <= yMax));
    }

    public boolean isColliding(int x, int y) {

        return ((xMin <= x) && (x <= xMax) && (yMin <= y) && (y <= yMax));
    }

    public boolean isColliding(Bar b2, int x, int y) {

        if ((b2.xMin <= x) && (x <= b2.xMax) && (b2.yMin <= y) && (y <= b2.yMax)) {
            return true;
        } else {
            return false;
        }
    }

}
