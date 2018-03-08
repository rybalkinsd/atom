package ru.atom.geometry;

public class Bar implements Collider {
    private int  xMin;
    private int  xMax;
    private int  yMin;
    private int  yMax;

    Bar() {
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
    }

    Bar(int x1, int y1, int x2, int y2) {
        xMin = Math.min(x1, x2);
        xMax = Math.max(x1, x2);
        yMin = Math.min(y1, y2);
        yMax = Math.max(y1, y2);
    }

    @Override
    public boolean isColliding(Collider obj) {
        if (obj.getClass() == Point.class) {
            Point pnt = (Point) obj;
            return (pnt.getX() >=  xMin) && (pnt.getX() <=  xMax)
                    && (pnt.getY() >=  yMin) && (pnt.getY() <=  yMax);
        }
        if (obj.getClass() == Bar.class) {
            Bar br = (Bar) obj;
            return (( xMin <= br.getMaxX()) && ( xMax >= br.getMinX())
                    && ( yMin <= br.getMaxY()) && ( yMax >= br.getMinY()));
        }
        return false;
    }

    public int getMinX() {
        return  xMin;
    }

    public int getMaxX() {
        return  xMax;
    }

    public int getMinY() {
        return  yMin;
    }

    public int getMaxY() {
        return  yMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bar bar = (Bar) o;

        return (this.xMin == bar.xMin) && (this.xMax == bar.xMax)
                && (this.yMin == bar.yMin) && (this.yMax == bar.yMax);
    }
}